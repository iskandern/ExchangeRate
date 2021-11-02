package com.example.exchangerate.utils

import com.example.exchangerate.data.database.ConversionCurrency
import com.example.exchangerate.data.database.SearchCurrency
import com.example.exchangerate.data.network.DateRate
import com.example.exchangerate.data.network.LatestRatesResponseData
import com.example.exchangerate.data.network.TimeSeriesRatesResponseData
import com.google.gson.JsonObject

fun JsonObject.getConversionCurrenciesFromJson() : LatestRatesResponseData {

    val queryJO = this.get("query").asJsonObject
    val timestamp = queryJO.get("timestamp").asLong
    val base = queryJO.get("base_currency").asString
    val searchCurrency = SearchCurrency(
        code = base,
        lastUpdateUnixTimestamp = timestamp
    )

    val ratesJO = this.get("data").asJsonObject
    val currencyList = ratesJO.keySet().map { code ->
        ConversionCurrency(
            code = code,
            rate = ratesJO.get(code).asDouble,
            baseCurrencyCode = base
        )
    }.filter { it.code != base }

    return LatestRatesResponseData(
        searchCurrency = searchCurrency,
        conversionCurrencies = currencyList
    )
}

fun JsonObject.getTimeSeriesDataFromJson(selected: String) : TimeSeriesRatesResponseData {

    val queryJO = this.get("query").asJsonObject
    val base = queryJO.get("base_currency").asString

    val datesJO = get("data").asJsonObject
    val dateRatesList = datesJO.keySet().map { date ->
        val dateJO = datesJO.get(date).asJsonObject
        val rate = dateJO.get(selected).asDouble
        val unixDate = getUNIXDate(date)
        DateRate(
            date = unixDate!!,
            rate = rate
        )
    }.toList()

    return TimeSeriesRatesResponseData(
        searchCurrencyCode = base,
        conversionCurrencyCode = selected,
        rates = dateRatesList
    )
}


