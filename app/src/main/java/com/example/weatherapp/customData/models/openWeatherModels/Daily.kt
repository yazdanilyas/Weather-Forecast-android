package com.example.weatherapp.customData.models.openWeatherModels

import com.google.gson.annotations.SerializedName


data class Daily(
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null,
    @SerializedName("moonrise") var moonrise: Int? = null,
    @SerializedName("moonset") var moonset: Int? = null,
    @SerializedName("moon_phase") var moonPhase: Double? = null,
    @SerializedName("temp") var temp: Temp? = null,
    @SerializedName("feels_like") var feelsLike: FeelsLike? = null,
    @SerializedName("pressure") var pressure: Int? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("dew_point") var dewPoint: Double? = null,
    @SerializedName("wind_speed") var windSpeed: Double? = null,
    @SerializedName("wind_deg") var windDeg: Int? = null,
    @SerializedName("weather") var weather: ArrayList<Weather>? = null,
    @SerializedName("clouds") var clouds: Int? = null,
    @SerializedName("pop") var pop: Double? = null,
    @SerializedName("rain") var rain: Double? = null,
    @SerializedName("uvi") var uvi: Double? = null,
    var hour: ArrayList<Hourly>? = null

)
   /* : BaseObservable(), Serializable {

    private var expandad: ObservableField<Int?> = ObservableField<Int?>(View.GONE)

    @Bindable
    fun getIsExpandad(): ObservableField<Int?> {
        return expandad
    }

    fun setIsExpandad(visibility: ObservableField<Int?>) {
        expandad = visibility
        notifyPropertyChanged(BR.isExpandad)
    }

    override fun toString(): String {
        return "Daily(dt=$dt, sunrise=$sunrise, sunset=$sunset, moonrise=$moonrise, moonset=$moonset, moonPhase=$moonPhase, temp=$temp, feelsLike=$feelsLike, pressure=$pressure, humidity=$humidity, dewPoint=$dewPoint, windSpeed=$windSpeed, windDeg=$windDeg, weather=$weather, clouds=$clouds, pop=$pop, rain=$rain, uvi=$uvi )"
    }

}
    */
