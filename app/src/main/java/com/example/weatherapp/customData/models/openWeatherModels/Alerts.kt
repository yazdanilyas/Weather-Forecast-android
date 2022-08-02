package com.example.weatherapp.customData.models.openWeatherModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Alerts(
    @SerializedName("sender_name")
    var senderName: String? = null,

    @SerializedName("event")
    var event: String? = null,

    @SerializedName("start")
    var start: Int? = null,

    @SerializedName("end")
    var end: Int? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("tags")
    var tags: ArrayList<String>? = null

) : Serializable {
    var mTotalWarning = 0
    override fun toString(): String {
        return "Alerts(senderName=$senderName, event=$event, start=$start, end=$end, description=$description, tags=$tags)"
    }
}