package com.example.exchangerate.data

import com.example.exchangerate.data.database.ConversionCurrency
import com.example.exchangerate.data.database.CurrencyDao
import com.example.exchangerate.data.network.CurrencyApi
import com.example.exchangerate.data.network.TimeSeriesRatesResponseData
import com.example.exchangerate.utils.*

class CurrencyRepository(
    private val currencyDao: CurrencyDao,
    private val currencyApi: CurrencyApi
) {

    suspend fun loadConversionCurrencies(base: String) : List<ConversionCurrency> {

        val lastSearch = currencyDao.getLastDate(base)
        if (lastSearch != null && !newRequestNeeds(lastSearch)) {
            val currencyListFromDB = currencyDao.getConversionCurrencies(base)
            if (currencyListFromDB.isNotEmpty()) {
                return currencyListFromDB
            }
        }

        val response = currencyApi.getLatestRates(base)
            .getConversionCurrenciesFromJson()

        currencyDao.updateCurrencySearchDate(response.searchCurrency)
        currencyDao.insertConversionCurrencies(response.conversionCurrencies)

        return response.conversionCurrencies
    }

    suspend fun loadTimeSeriesData(base: String, selected: String): TimeSeriesRatesResponseData {

        val lastBaseDate = currencyDao.getLastDate(base) ?: getCurrentUNIXDate()
        val loadTimeInterval = getStartEndTimePoints(lastBaseDate)

        return currencyApi.getTimeSeriesRates(
            base = base,
            startDate = loadTimeInterval.first,
            endDate = loadTimeInterval.second
        )
            .getTimeSeriesDataFromJson(selected)

    }
}