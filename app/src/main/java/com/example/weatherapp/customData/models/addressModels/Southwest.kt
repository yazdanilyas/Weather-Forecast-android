package com.example.weatherapp.customData.models.addressModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Southwest(
    @SerializedName("lat")
    @Expose
    var lat: Double? = null,

    @SerializedName("lng")
    @Expose
    var lng: Double? = null
) : Serializable {
    override fun toString(): String {
        return "Southwest(lat=$lat, lng=$lng)"
    }
}