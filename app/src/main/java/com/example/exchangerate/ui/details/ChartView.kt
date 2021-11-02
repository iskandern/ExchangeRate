package com.example.exchangerate.ui.details

import com.github.mikephil.charting.data.LineDataSet
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface ChartView : MvpView {

    @AddToEndSingle
    fun showChart(dataset: LineDataSet, dateNames: List<String>)

}