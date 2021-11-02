package com.example.exchangerate.ui.details

import android.os.Bundle
import com.example.exchangerate.databinding.ActivityChartBinding
import com.example.exchangerate.utils.TIME_SERIES_DATA
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope
import com.github.mikephil.charting.formatter.ValueFormatter


class ChartActivity : MvpAppCompatActivity(), ChartView, AndroidScopeComponent {

    override val scope: Scope by activityScope()

    private val presenter by moxyPresenter {
        val presenter: ChartPresenter by inject()
        presenter
    }

    private lateinit var binding: ActivityChartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChartBinding.inflate(layoutInflater)

        setupChart()
        presenter.onAttach(intent.extras?.get(TIME_SERIES_DATA))

        setContentView(binding.root)
    }

    private fun setupChart() {
        with(binding.chart) {
            isDragEnabled = true
            isScaleXEnabled = false
        }
    }

    override fun showChart(dataset: LineDataSet, dateNames: List<String>) {

        with(binding.chart) {
            data = LineData(dataset)
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return dateNames[value.toInt()]
                }
            }
        }
    }
}