package com.example.weatherapp.utilites

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.application.WeatherApplication
import com.example.weatherapp.customData.models.openWeatherModels.Current
import com.example.weatherapp.customData.models.openWeatherModels.Daily
import com.example.weatherapp.customData.models.openWeatherModels.Hourly
import com.example.weatherapp.customData.models.openWeatherModels.OneCallModel
import com.example.weatherapp.customData.models.room.Locations
import com.example.weatherapp.source.local.prefs.PrefUtils.Companion.getString
import java.util.*
import kotlin.math.floor


@BindingAdapter(value = ["setCurrentWeatherImg"])
fun setCurrentWeatherImg(pImageView: AppCompatImageView, pModel: OneCallModel?) {
    if (pModel == null) return
    val iconCode = pModel.current?.weather?.get(0)?.icon
    val iconUrl = "http://openweathermap.org/img/wn/${iconCode}@2x.png"
    Glide.with(pImageView.context).load(iconUrl).into(pImageView)
}

@BindingAdapter(value = ["setHourlyWeatherImg"])
fun setHourlyWeatherImg(pImageView: ImageView, pModel: Hourly?) {
    val iconCode = pModel?.weather?.get(0)?.icon
    val iconUrl = "http://openweathermap.org/img/wn/${iconCode}@2x.png"
    Glide.with(pImageView.context).load(iconUrl).into(pImageView)
}

@BindingAdapter(value = ["setTimeData"])
fun setTimeData(pTextView: TextView, hour: Hourly) {
    try {
        pTextView.text = hour.dt?.let { time_epoch ->
            Utilities.manipulateTimeForAmPm(time_epoch.toString())
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

@SuppressLint("DefaultLocale", "SetTextI18n")
@BindingAdapter(value = ["setTempDegree"])
fun setTempDegree(pTextView: TextView, pModel: Hourly) {
    var temprature = floor(pModel.temp ?: 0.0).toInt().toString()
    Log.d("TAG", "setTempDegree: $temprature")
    if (temprature == "-0") {
        pTextView.text = "0" + 0x00B0.toChar()
    } else {
        pTextView.text = temprature + 0x00B0.toChar()
    }
}

@SuppressLint("DefaultLocale", "SetTextI18n")
@BindingAdapter(value = ["setCurrentTempDegree"])
fun setCurrentTempDegree(pTextView: TextView, pModel: Current?) {
    var temprature = floor(pModel?.temp ?: 0.0).toInt().toString()
    Log.d("TAG", "setTempDegree: $temprature")
    if (temprature == "-0") {
        pTextView.text = "0" + 0x00B0.toChar()
    } else {
        pTextView.text = temprature + 0x00B0.toChar()
    }
}

@BindingAdapter("setTitleDay")
fun setTitleDay(pTextView: TextView, pModel: Daily) {
    if (pModel == null) return
    val day = Utilities.dayOfWeek(pModel.dt.let { dt ->
        dt?.toLong()?.let { getDate(it) }
    })
    Log.d("WEEKDAY", "is_$day")
    SetDays(pTextView, day)
}

private fun getDate(time: Long): String {
    val cal = Calendar.getInstance(
        TimeZone.getTimeZone(
            WeatherApplication.context.let { context ->
                getString(
                    context,
                    CommonKeys.KEY_TIMEZONE
                )
            }
        )
    )
    cal.timeInMillis = time * 1000
    return DateFormat.format("yyyy-MM-dd HH:mm:ss", cal).toString()
}


private fun SetDays(pTextView: TextView, day: Int) {
    val days: String
    when (day) {
        Calendar.SUNDAY -> {
            // Current day is Sunday
            days = pTextView.context.resources.getString(R.string.sunday)
            Log.d("WEEKDAY", "is_sunday")
        }
        Calendar.MONDAY -> {
            // Current day is Monday
            days = pTextView.context.resources.getString(R.string.monday)
            Log.d("WEEKDAY", "is_Monday")
        }
        Calendar.TUESDAY -> {
            days = pTextView.context.resources.getString(R.string.tusday)
            Log.d("WEEKDAY", "is_Tues")
        }
        Calendar.WEDNESDAY -> {
            days = pTextView.context.resources.getString(R.string.wednesday)
            Log.d("WEEKDAY", "is_Wed")
        }
        Calendar.THURSDAY -> {
            days = pTextView.context.resources.getString(R.string.thursday)
            Log.d("WEEKDAY", "is_Thursday")
        }
        Calendar.FRIDAY -> {
            days = pTextView.context.resources.getString(R.string.friday)
            Log.d("WEEKDAY", "is_Friday")
        }
        Calendar.SATURDAY -> {
            days = pTextView.context.resources.getString(R.string.saturday)
            Log.d("WEEKDAY", "is_Saturday")
        }
        else -> {
            days = ""
        }
    }
    pTextView.text = days
}

@BindingAdapter("setThumbnail")
fun setThumbnail(pImageView: ImageView, pModel: Daily?) {
    if (pModel == null) return


    val iconCode = pModel.weather?.get(0)?.icon

    val iconUrl = "http://openweathermap.org/img/wn/${iconCode}@2x.png"

    Glide.with(pImageView.context).load(iconUrl).into(pImageView)
    Log.d("TAGG", "setThumbnail: $iconUrl")
}

@SuppressLint("DefaultLocale", "SetTextI18n")
@BindingAdapter(value = ["setTemperature", "temprature"])
fun setTemperature(pTextView: TextView, pModel: Daily?, temprature: Int) {
    if (pModel == null) return
    var tempratue: String? = null
    when (temprature) {
        1 -> tempratue =
            floor(pModel.temp?.min ?: 0.0).toInt().toString()
        2 -> tempratue = floor(pModel.temp?.max ?: 0.0).toInt().toString()
        else -> {}
    }
    if (tempratue == "-0") {
        pTextView.text = "0" + 0x00B0.toChar()
    } else {
        pTextView.text =
            String.format(
                "%.0f",
                tempratue?.toDouble()?.let { temprature ->
                    Math.floor(temprature)
                }) + 0x00B0.toChar()
    }

}

@SuppressLint("SetTextI18n")
@BindingAdapter("setLocationText")
fun setLocationText(pTextView: TextView, pLocations: Locations) {
    if (pLocations.isCurrent == true) {
        pTextView.text = pTextView.context.resources.getString(R.string.current_location)
    } else {
        pTextView.text = "${pLocations.cityName} , ${pLocations.countryName}"
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setAirPressure")
fun setAirPressure(pTextView: TextView, pModel: OneCallModel?) {
    if (pModel != null) {
        val pressure=pModel.current?.pressure
        val unit=pTextView.context.getString(R.string.hpa)
        pTextView.text =
            "${pTextView.context.getString(R.string.pressure)} $pressure $unit"
    }

}

@SuppressLint("SetTextI18n")
@BindingAdapter("setHumidity")
fun setHumidity(pTextView: TextView, pModel: OneCallModel?) {
    if (pModel != null) {
        pTextView.text =
            "${pTextView.context.getString(R.string.humidity)} ${pModel.current?.humidity}"
    }
}




