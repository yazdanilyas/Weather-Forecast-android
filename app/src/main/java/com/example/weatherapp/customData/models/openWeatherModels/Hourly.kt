package com.example.weatherapp.customData.models.openWeatherModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Hourly(
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("feels_like") var feelsLike: Double? = null,
    @SerializedName("pressure") var pressure: Double? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("dew_point") var dewPoint: Double? = null,
    @SerializedName("wind_dir") var windDir: String? = null,
    @SerializedName("uvi") var uvi: Double? = null,
    @SerializedName("clouds") var clouds: Int? = null,
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("wind_speed") var windSpeed: Double? = null,
    @SerializedName("wind_deg") var windDeg: Int? = null,
    @SerializedName("wind_gust") var windGust: Double? = null,
    @SerializedName("weather") var weather: ArrayList<Weather>? = null,
    @SerializedName("pop") var pop: Double? = null
) : Serializable {
    override fun toString(): String {
        return "Hourly(dt=$dt, temp=$temp, feelsLike=$feelsLike, pressure=$pressure, humidity=$humidity, dewPoint=$dewPoint,windDir =$windDir, uvi=$uvi, clouds=$clouds, visibility=$visibility, windSpeed=$windSpeed, windDeg=$windDeg, windGust=$windGust, weather=$weather, pop=$pop)"
    }
}