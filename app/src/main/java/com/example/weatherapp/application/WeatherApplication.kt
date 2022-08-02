package com.example.weatherapp.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
//        Places.initialize(applicationContext, getString(R.string.places_api))
//        Places.createClient(this)
    }

    companion object {
        @JvmStatic
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
        @JvmName("getContext1")
        fun getContext(): Context {
            return context
        }
    }
}