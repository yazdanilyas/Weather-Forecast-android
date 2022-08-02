package com.example.weatherapp.ui.activities.main

import com.example.weatherapp.customData.models.openWeatherModels.Daily
import com.example.weatherapp.customData.models.openWeatherModels.Hourly
import com.example.weatherapp.customData.models.openWeatherModels.OneCallModel
import com.example.weatherapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel() {
    var hourlyForecastData: MutableList<Hourly>? = ArrayList()
    var dailyForecastData: MutableList<Daily>? = ArrayList()
    var oneCallResponse: OneCallModel? = null

}