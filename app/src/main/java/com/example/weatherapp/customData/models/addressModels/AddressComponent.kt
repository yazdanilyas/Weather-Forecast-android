package com.example.weatherapp.customData.models.addressModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddressComponent(
    @SerializedName("long_name")
    @Expose
    var longName: String? = null,

    @SerializedName("short_name")
    @Expose
    var shortName: String? = null,

    @SerializedName("types")
    @Expose
    var types: List<String>? = null
) : Serializable {
    override fun toString(): String {
        return "AddressComponent(longName=$longName, shortName=$shortName, types=$types)"
    }
}