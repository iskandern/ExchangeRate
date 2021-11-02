package com.example.exchangerate.data.network

import android.os.Parcelable
import com.example.exchangerate.data.database.ConversionCurrency
import com.example.exchangerate.data.database.SearchCurrency
import kotlinx.parcelize.Parcelize

data class LatestRatesResponseData(
    val searchCurrency: SearchCurrency,
    val conversionCurrencies: List<ConversionCurrency>
)

@Parcelize
data class DateRate(
    val date: Long,
    val rate: Double
) : Parcelable

@Parcelize
data class TimeSeriesRatesResponseData(
    val searchCurrencyCode: String,
    val conversionCurrencyCode: String,
    val rates: List<DateRate>
) : Parcelable