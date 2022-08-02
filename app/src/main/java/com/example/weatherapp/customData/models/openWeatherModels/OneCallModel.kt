package com.example.weatherapp.customData.models.openWeatherModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class OneCallModel(
    @SerializedName("lat")
    var lat: Double? = null,

    @SerializedName("lon")
    var lon: Double? = null,

    @SerializedName("timezone")
    var timezone: String? = null,

    @SerializedName("timezone_offset")
    var timezoneOffset: Int? = null,

    @SerializedName("current")
    var current: Current? = null,

    @SerializedName("minutely")
    var minutely: ArrayList<Minutely>? = null,

    @SerializedName("hourly")
    var hourly: ArrayList<Hourly>? = null,

    @SerializedName("daily")
    var daily: ArrayList<Daily>? = null,

    @SerializedName("alerts")
    var alerts: ArrayList<Alerts>? = null

) : Serializable
