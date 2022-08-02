package com.example.weatherapp.customData.models.openWeatherModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Temp(

    @SerializedName("day")
    var day: Double? = null,

    @SerializedName("min")
    var min: Double? = null,

    @SerializedName("max")
    var max: Double? = null,

    @SerializedName("night")
    var night: Double? = null,

    @SerializedName("eve")
    var eve: Double? = null,

    @SerializedName("morn")
    var morn: Double? = null

) : Serializable {
    override fun toString(): String {
        return "Temp(day=$day, min=$min, max=$max, night=$night, eve=$eve, morn=$morn)"
    }
}