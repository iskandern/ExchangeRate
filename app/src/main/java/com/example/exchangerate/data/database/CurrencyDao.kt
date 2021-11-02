package com.example.exchangerate.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM ConversionCurrency WHERE baseCurrencyCode IS :baseCode")
    suspend fun getConversionCurrencies(baseCode: String): List<ConversionCurrency>

    @Query("SELECT lastUpdateUnixTimestamp FROM SearchCurrency WHERE code IS :currency LIMIT 1")
    suspend fun getLastDate(currency: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCurrencySearchDate(searchCurrency: SearchCurrency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversionCurrencies(ConversionCurrencies: List<ConversionCurrency>)
}