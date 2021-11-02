package com.example.exchangerate.ui.main

import com.example.exchangerate.data.database.ConversionCurrency
import com.example.exchangerate.data.network.TimeSeriesRatesResponseData
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface MainView : MvpView {

    @Skip
    fun showTimeSeriesDataInNewActivity(tsData: TimeSeriesRatesResponseData)

    @AddToEndSingle
    fun showConversionList(items: List<ConversionCurrency>, isNewBase: Boolean)

    @AddToEndSingle
    fun hideProgress()

    @AddToEndSingle
    fun showProgress()

    @AddToEndSingle
    fun showError(message: String)

    @AddToEndSingle
    fun hideError()
}