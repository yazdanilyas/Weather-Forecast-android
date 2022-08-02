package com.example.weatherapp.customData.models.addressModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Result(
    @SerializedName("address_components")
    @Expose
    var addressComponents: List<AddressComponent>? = null,

    @SerializedName("formatted_address")
    @Expose
    var formattedAddress: String? = null,

    @SerializedName("geometry")
    @Expose
    var geometry: Geometry? = null,

    @SerializedName("place_id")
    @Expose
    var placeId: String? = null,

    @SerializedName("types")
    @Expose
    var types: List<String>? = null
) : Serializable {
    override fun toString(): String {
        return "Result(addressComponents=$addressComponents, formattedAddress=$formattedAddress, geometry=$geometry, placeId=$placeId, types=$types)"
    }
}