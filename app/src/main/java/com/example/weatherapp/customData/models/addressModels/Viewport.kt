package com.example.weatherapp.customData.models.addressModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Viewport(
    @SerializedName("northeast")
    @Expose
    var northeast: Northeast_? = null,

    @SerializedName("southwest")
    @Expose
    var southwest: Southwest_? = null
) : Serializable {
    override fun toString(): String {
        return "Viewport(northeast=$northeast, southwest=$southwest)"
    }
}