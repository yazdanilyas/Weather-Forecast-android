package com.example.weatherapp.customData.models.addressModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlacesResult(
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("error_message")
    @Expose
    var errorMessage: String? = null
) : Serializable {
    override fun toString(): String {
        return "PlacesResult(results=$results, status=$status, errorMessage=$errorMessage)"
    }
}