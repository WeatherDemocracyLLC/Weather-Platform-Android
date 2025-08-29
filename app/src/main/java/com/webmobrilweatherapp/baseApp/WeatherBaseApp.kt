package com.webmobrilweatherapp.baseApp

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class WeatherBaseApp : Application() {


    companion object {
        lateinit var instance: WeatherBaseApp
            private set
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this


    }


}