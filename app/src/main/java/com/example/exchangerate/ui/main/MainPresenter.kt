package com.example.exchangerate.ui.main

import com.example.exchangerate.data.database.ConversionCurrency
import com.example.exchangerate.data.CurrencyRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import moxy.MvpPresenter
import retrofit2.HttpException
import java.net.UnknownHostException

class MainPresenter(
    private val currencyRepository: CurrencyRepository
    ) : MvpPresenter<MainView>() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var lastBase = ""
    private val baseMutex = Mutex()

    private fun loadCurrencies(base: String) {

        val loadExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            runBlocking {
                when (throwable) {
                    is UnknownHostException -> {
                        withContext(Dispatchers.Main) {
                            viewState.hideProgress()
                            viewState.showConversionList(emptyList(), false)
                            viewState.showError("Check inet connection")
                        }
                    }
                    else -> {
                        withContext(Dispatchers.Main) {
                            viewState.hideProgress()
                            viewState.showConversionList(emptyList(), false)
                            viewState.showError(throwable.message ?: "connection error")
                        }
                    }
                }
            }
        }

        scope.launch(loadExceptionHandler + SupervisorJob()) {

            withContext(Dispatchers.Main) {
                viewState.showProgress()
            }

            val currencies = currencyRepository.loadConversionCurrencies(base)


            withContext(Dispatchers.Main) {
                baseMutex.withLock {
                    val isNewBase = lastBase != base
                    viewState.showConversionList(currencies, isNewBase)
                    viewState.hideProgress()
                    lastBase = base
                }
            }
        }
    }

    fun getSelectedBaseHandler() : (String) -> Unit {
        return { loadCurrencies(it) }
    }

    fun handleCurrencySelection(selected: ConversionCurrency) {

        val loadExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            runBlocking {
                when (throwable) {
                    is UnknownHostException -> {
                        withContext(Dispatchers.Main) {
                            viewState.hideProgress()
                            viewState.showError("Check inet connection")
                        }
                    }
                    is HttpException -> {
                        withContext(Dispatchers.Main) {
                            viewState.hideProgress()
                            viewState.showError(
                                "No exchange rate data is available for the selected currency"
                            )
                        }
                    }
                    else -> {
                        withContext(Dispatchers.Main) {
                            viewState.hideProgress()
                            viewState.showError(throwable.message ?: "connection error")
                        }
                    }
                }
            }
        }

        lateinit var base: String
        scope.launch(loadExceptionHandler + SupervisorJob()) {
            baseMutex.withLock {
                base = lastBase
            }

            withContext(Dispatchers.Main) {
                viewState.showProgress()
            }

            val tsData = currencyRepository.loadTimeSeriesData(base, selected.code)

            withContext(Dispatchers.Main) {
                viewState.hideProgress()
                viewState.showTimeSeriesDataInNewActivity(tsData)
            }
        }

    }

}