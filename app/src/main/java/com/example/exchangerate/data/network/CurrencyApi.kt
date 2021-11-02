package com.example.exchangerate.data.network

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest")
    suspend fun getLatestRates(@Query("base_currency") base: String) : JsonObject

    @GET("historical")
    suspend fun getTimeSeriesRates(
        @Query("date_from") startDate: String,
        @Query("date_to") endDate: String,
        @Query("base_currency") base: String
    ) : JsonObject
}