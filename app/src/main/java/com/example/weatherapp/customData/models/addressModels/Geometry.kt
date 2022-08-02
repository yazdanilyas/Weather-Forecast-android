package com.example.weatherapp.customData.models.addressModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Geometry(
    @SerializedName("bounds")
    @Expose
    var bounds: Bounds? = null,

    @SerializedName("location")
    @Expose
    var location: Location? = null,

    @SerializedName("location_type")
    @Expose
    var locationType: String? = null,

    @SerializedName("viewport")
    @Expose
    var viewport: Viewport? = null
) : Serializable {
    override fun toString(): String {
        return "Geometry(bounds=$bounds, location=$location, locationType=$locationType, viewport=$viewport)"
    }
}