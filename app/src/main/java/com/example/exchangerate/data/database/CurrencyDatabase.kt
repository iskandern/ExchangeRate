package com.example.exchangerate.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [SearchCurrency::class, ConversionCurrency::class],
    version = 5,
    exportSchema = false
)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao() : CurrencyDao
}
