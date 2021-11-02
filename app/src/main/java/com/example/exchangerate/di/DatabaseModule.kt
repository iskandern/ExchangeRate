package com.example.exchangerate.di

import androidx.room.Room
import com.example.exchangerate.data.database.CurrencyDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            CurrencyDatabase::class.java,
            "currency_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<CurrencyDatabase>().currencyDao() }
}
