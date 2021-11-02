package com.example.exchangerate.ui.details

import com.example.exchangerate.data.network.TimeSeriesRatesResponseData
import com.example.exchangerate.utils.getDay
import com.example.exchangerate.utils.getStringDate
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import moxy.MvpPresenter

class ChartPresenter(
) : MvpPresenter<ChartView>() {

    private fun showTimeSeriesCurrenciesData(timeSeriesData: TimeSeriesRatesResponseData) {

        val tsData = timeSeriesData.rates.mapIndexed { position, tsDate ->
            Entry(position.toFloat(), tsDate.rate.toFloat())
        }

        val startDateStr = getStringDate(timeSeriesData.rates.first().date)
        val endDateStr = getStringDate(timeSeriesData.rates.last().date)
        val baseCode = timeSeriesData.searchCurrencyCode
        val comparedCode = timeSeriesData.conversionCurrencyCode

        val dataset = LineDataSet(
            tsData,
            "7-day rates $baseCode / $comparedCode from $startDateStr to $endDateStr"
        )

        val datesList = timeSeriesData.rates.map {
            getDay(it.date)
        }

        viewState.showChart(dataset, datesList)
    }

    fun onAttach(timeSeriesData: Any?) {
        if (timeSeriesData !is TimeSeriesRatesResponseData) {
            return
        }
        showTimeSeriesCurrenciesData(timeSeriesData)
    }
}