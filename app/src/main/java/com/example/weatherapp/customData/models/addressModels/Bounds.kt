package com.example.weatherapp.customData.models.addressModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Bounds(
    @SerializedName("northeast")
    @Expose
    var northeast: Northeast? = null,

    @SerializedName("southwest")
    @Expose
    var southwest: Southwest? = null
) : Serializable {
    override fun toString(): String {
        return "Bounds(northeast=$northeast, southwest=$southwest)"
    }
}