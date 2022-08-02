package com.example.weatherapp.customData.models.openWeatherModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Rain(

    @SerializedName("1h")
    var _1h: Double? = null

) : Serializable {
    override fun toString(): String {
        return "Rain(_1h=$_1h)"
    }
}