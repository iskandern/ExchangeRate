package com.example.exchangerate.di

import com.example.exchangerate.data.CurrencyRepository
import com.example.exchangerate.ui.adapter.ConversionCurrencyAdapter
import com.example.exchangerate.ui.details.ChartActivity
import com.example.exchangerate.ui.details.ChartPresenter
import com.example.exchangerate.ui.main.MainActivity
import com.example.exchangerate.ui.main.MainPresenter
import com.example.exchangerate.ui.search.SearchFragment
import com.example.exchangerate.ui.search.SearchPresenter
import org.koin.dsl.module

val UIModule = module {

    single { CurrencyRepository(get(), get()) }

    scope<MainActivity> {
        scoped<MainPresenter> { MainPresenter(get()) }
        scoped<ConversionCurrencyAdapter> { ConversionCurrencyAdapter() }
    }

    scope<SearchFragment> {
        scoped<SearchPresenter> { SearchPresenter() }
    }

    scope<ChartActivity> {
        scoped<ChartPresenter> { ChartPresenter() }
    }

}
