package com.example.weatherapp.customData.models.openWeatherModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Minutely(

    @SerializedName("dt")
    var dt: Int? = null,

    @SerializedName("precipitation")
    var precipitation: Double? = null

) : Serializable {
    override fun toString(): String {
        return "Minutely(dt=$dt, precipitation=$precipitation)"
    }
}