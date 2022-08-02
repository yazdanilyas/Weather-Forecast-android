package com.example.weatherapp.customData.models.openWeatherModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Weather(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("main") var main: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("icon") var icon: String? = null

) : Serializable {
    override fun toString(): String {
        return "Weather(id=$id, main=$main, description=$description, icon=$icon)"
    }
}