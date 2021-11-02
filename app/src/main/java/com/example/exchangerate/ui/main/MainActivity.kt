package com.example.exchangerate.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.exchangerate.data.database.ConversionCurrency
import com.example.exchangerate.ui.adapter.ConversionCurrencyAdapter
import com.example.exchangerate.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope
import android.util.DisplayMetrics
import android.view.Display
import com.example.exchangerate.data.network.TimeSeriesRatesResponseData
import com.example.exchangerate.ui.details.ChartActivity
import com.example.exchangerate.utils.TIME_SERIES_DATA
import kotlin.math.roundToInt


class MainActivity : MvpAppCompatActivity(), MainView, AndroidScopeComponent {

    override val scope: Scope by activityScope()

    private val presenter by moxyPresenter {
        val presenter: MainPresenter by inject()
        presenter
    }

    private val currencyAdapter: ConversionCurrencyAdapter by inject()
    private lateinit var binding: ActivityMainBinding
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.rvCurrency.adapter = currencyAdapter
        setupRVLayout()

        currencyAdapter.setItemClickListener { presenter.handleCurrencySelection(it) }

        setContentView(binding.root)
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
        binding.rvCurrency.visibility = View.INVISIBLE
    }

    override fun hideProgress() {
        binding.rvCurrency.visibility = View.VISIBLE
        binding.progress.visibility = View.INVISIBLE
    }

    override fun showError(message: String) {
        alertDialog = AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton(
                "OK"
            ) { _, _ -> hideError() }
            .show()
    }

    override fun hideError() {
        alertDialog.dismiss()
    }

    override fun showTimeSeriesDataInNewActivity(tsData: TimeSeriesRatesResponseData) {
        val intent = Intent(this, ChartActivity::class.java)
        intent.putExtra(TIME_SERIES_DATA, tsData)
        startActivity(intent)
    }

    override fun showConversionList(items: List<ConversionCurrency>, isNewBase: Boolean) {
       currencyAdapter.setCategories(items)
        if (isNewBase) {
            binding.rvCurrency.scrollToPosition(0)
        }
    }

    private fun setupRVLayout() {
        val display: Display = windowManager.getDefaultDisplay()
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        val density = resources.displayMetrics.density
        val dpWidth = outMetrics.widthPixels / density
        val columns = ((dpWidth - 24) / 170).roundToInt()
        binding.rvCurrency.layoutManager = GridLayoutManager(this, columns)
    }

    fun getSelectBaseHandler(): (String) -> Unit {
        return presenter.getSelectedBaseHandler()
    }
}