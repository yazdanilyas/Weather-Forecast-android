package com.example.weatherapp.customData.models.openWeatherModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class FeelsLike(

    @SerializedName("day")
    var day: Double? = null,

    @SerializedName("night")
    var night: Double? = null,

    @SerializedName("eve")
    var eve: Double? = null,

    @SerializedName("morn")
    var morn: Double? = null

) : Serializable {
    override fun toString(): String {
        return "FeelsLike(day=$day, night=$night, eve=$eve, morn=$morn)"
    }
}