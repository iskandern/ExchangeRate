package com.example.exchangerate

import android.app.Application
import com.example.exchangerate.di.UIModule
import com.example.exchangerate.di.databaseModule
import com.example.exchangerate.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(networkModule)
            modules(databaseModule)
            modules(UIModule)
        }
    }

}