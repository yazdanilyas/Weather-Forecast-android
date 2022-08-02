package com.example.weatherapp.utilites

import android.content.Context
import android.net.ConnectivityManager
import android.text.format.DateFormat
import android.util.Log
import com.example.weatherapp.R
import com.example.weatherapp.application.WeatherApplication
import com.example.weatherapp.customData.models.openWeatherModels.Current
import com.example.weatherapp.source.local.prefs.PrefUtils
import com.example.weatherapp.utilites.CommonKeys.KEY_TIMEZONE
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

class Utilities {
    fun DayOfWeek(date: String?, pContext: Context): String {
        return try {
            val day: String
            val simpleDateformat = SimpleDateFormat("yyyy-MM-dd")
            val now = simpleDateformat.parse(date)
            val calendar = Calendar.getInstance()
            calendar.time = now
            day = SetDays(calendar[Calendar.DAY_OF_WEEK], pContext)
            day
        } catch (e: Exception) {
            ""
        }
    }

    private fun SetDays(day: Int, pContext: Context): String {
        var dayName = ""
        when (day) {
            Calendar.SUNDAY -> {
                // Current day is Sunday
                dayName = pContext.resources.getString(R.string.sunday)
                Log.d("WEEKDAY", "is_sunday")
            }
            Calendar.MONDAY -> {
                // Current day is Monday
                dayName = pContext.resources.getString(R.string.monday)
                Log.d("WEEKDAY", "is_Monday")
            }
            Calendar.TUESDAY -> {
                // etc.
                dayName = pContext.resources.getString(R.string.tusday)
                Log.d("WEEKDAY", "is_Tues")
            }
            Calendar.WEDNESDAY -> {
                // etc.
                dayName = pContext.resources.getString(R.string.wednesday)
                Log.d("WEEKDAY", "is_Wed")
            }
            Calendar.THURSDAY -> {
                // etc.
                dayName = pContext.resources.getString(R.string.thursday)
                Log.d("WEEKDAY", "is_Thurday")
            }
            Calendar.FRIDAY -> {
                // etc.
                dayName = pContext.resources.getString(R.string.friday)
                Log.d("WEEKDAY", "is_Friday")
            }
            Calendar.SATURDAY -> {
                // etc.
                dayName = pContext.resources.getString(R.string.saturday)
                Log.d("WEEKDAY", "is_Saturday")
            }
        }
        return dayName
    }

    companion object {
        private const val TAG = "Utilities"

        @JvmStatic
        fun isNetworkAvailable(pContext: Context): Boolean {
            val connectivityManager =
                pContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }


        @JvmStatic
        fun manipulateTimeForAmPm(time: String): String {
            var formatted_time = "12:00 AM"
            try {
                val cal = Calendar.getInstance(
                    TimeZone.getTimeZone(
                        PrefUtils.getString(
                            WeatherApplication.getContext(),
                            KEY_TIMEZONE
                        )
                    )
                )
                cal.timeInMillis = time.toLong() * 1000L
                formatted_time = DateFormat.format("haa", cal).toString()
            } catch (ex: Exception) {
                Log.e("Exception", "ManiuplateTime with exception ", ex)
            }
            return formatted_time.uppercase(Locale.getDefault())
        }

        @JvmStatic
        fun dayOfWeek(date: String?): Int {
            return try {
                val day: Int
                val simpleDateformat = SimpleDateFormat("yyyy-MM-dd")
                val now = simpleDateformat.parse(date)
                val calendar = Calendar.getInstance()
                calendar.time = now
                day = calendar[Calendar.DAY_OF_WEEK]
                day
            } catch (e: Exception) {
                0
            }
        }

        fun convertTemperatureToDegree(current: Current?): String {
            val temprature = floor(current?.temp ?: 0.0).toInt().toString()
            Log.d("TAG", "setTempDegree: $temprature")
            if (temprature == "-0") {
                return "0" + 0x00B0.toChar()
            } else {
                return temprature + 0x00B0.toChar()
            }
        }

    }


}
