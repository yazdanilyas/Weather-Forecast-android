package com.example.weatherapp.ui.activities.locations

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.R
import com.example.weatherapp.application.WeatherApplication
import com.example.weatherapp.customData.models.room.Locations
import com.example.weatherapp.datamanager.DataManager
import com.example.weatherapp.source.local.prefs.PrefUtils
import com.example.weatherapp.ui.base.BaseViewModel
import com.example.weatherapp.utilites.CommonKeys
import com.example.weatherapp.utilites.CommonKeys.KEY_ADDRESS
import com.example.weatherapp.utilites.CommonKeys.KEY_CITY
import com.example.weatherapp.utilites.CommonKeys.KEY_COUNTRY
import com.example.weatherapp.utilites.CommonKeys.KEY_IS_CURRENT_LOCATION
import com.example.weatherapp.utilites.CommonKeys.KEY_LAT
import com.example.weatherapp.utilites.CommonKeys.KEY_LNG
import com.example.weatherapp.utilites.CommonKeys.KEY_STATE
import com.example.weatherapp.utilites.Utilities.Companion.isNetworkAvailable
import com.google.android.libraries.places.widget.Autocomplete
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LocationSettingViewModel  @Inject constructor() : BaseViewModel() {
    val mLocationsList: MutableList<Locations> = ArrayList()
    var cityName: String? = null
    var stateName: String? = null
    var countryName: String? = null
    var address: String? = null
    lateinit var latitude: String
    lateinit var longitude: String

    //    var toast = MutableLiveData<ToastModel>()
    var initializeAutoCompletePlace = MutableLiveData<Void?>()
    var callReverseGeocodeApi = MutableLiveData<Void?>()

    fun getLocationsObserver(): LiveData<List<Locations>> {
        return DataManager.instance.allLocationsObserver
    }

    private fun addLocationDataToRoomDb(address: Address) {
        DataManager.instance.insertLocation(
            Locations(
                address.locality,
                address.adminArea,
                address.countryName,
                address.latitude.toString(),
                address.longitude.toString(),
                address.getAddressLine(0),
                false
            )
        )
    }

    fun handleLocationClick(activity: LocationSettingActivity, `object`: Any?) {
        if (isNetworkAvailable(activity)) {
            val locations = `object` as Locations

            latitude = locations.latitude.toString()
            longitude = locations.longitude.toString()
            cityName = locations.cityName
            stateName = locations.stateName
            countryName = locations.countryName
            address = locations.address

            PrefUtils.setString(activity, KEY_LAT, latitude)
            PrefUtils.setString(activity, KEY_LNG, longitude)
            PrefUtils.setString(activity, KEY_CITY, cityName)
            PrefUtils.setString(activity, KEY_STATE, stateName)
            PrefUtils.setString(activity, KEY_COUNTRY, countryName)
            PrefUtils.setString(activity, KEY_ADDRESS, address)

            if (locations.isCurrent == true) {
//                MyWorkManager.startService()
                PrefUtils.setBoolean(
                    WeatherApplication.getContext(),
                    KEY_IS_CURRENT_LOCATION,
                    true
                )
            } else {
//                MyWorkManager.stopService()
                PrefUtils.setBoolean(
                    WeatherApplication.getContext(),
                    KEY_IS_CURRENT_LOCATION,
                    false
                )
            }
            PrefUtils.setBoolean(
                WeatherApplication.getContext(),
                CommonKeys.KEY_IS_FROM_LOCATIONS_SCREEN, true
            )
            activity.finish()
        } else {
            Toast.makeText(
                WeatherApplication.getContext(),
                "Check internet connection",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun handleNewLocationClick(activity: Activity) {
        if (isNetworkAvailable(activity)) {
            try {
                if (mLocationsList.size < 5) {
                    initializeAutoCompletePlace.postValue(null)
                } else {
                    Toast.makeText(
                        activity,
                        activity.getString(R.string.location_limit_text),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (ignored: Exception) {
            }
        } else {
            Toast.makeText(
                WeatherApplication.getContext(),
                "Check internet connection",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun handlePlacePickerOkResponse(activity: Activity, data: Intent) {
        try {
            val place = Autocomplete.getPlaceFromIntent(data)
            latitude = Objects.requireNonNull(place.latLng).latitude.toString()
            longitude = place.latLng?.longitude.toString()
            callReverseGeocodeApi.postValue(null)
            Log.d(TAG, "handlePlacePickerOkResponse: Latitude: $latitude & Longitude: $longitude")
            // get location details from Google Geocoder Api
            val geocoder = Geocoder(activity, Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(
                    latitude.toDouble(), longitude.toDouble(), 1
                )
                addLocationDataToRoomDb(addresses[0])
                Log.d(TAG, "handlePlacePickerOkResponse: ${addresses[0].countryName}")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (exception: IllegalArgumentException) {
            Toast.makeText(
                WeatherApplication.getContext(),
                "Internal server error",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun handlePlacePickerErrorResponse(data: Intent) {
        try {
            val status = Autocomplete.getStatusFromIntent(
                Objects.requireNonNull(data)
            )
            Log.e("LocationSetting", status.statusMessage ?: "")
        } catch (e: Exception) {
            Log.e("LocationSetting", "onActivityResult: ", e)
        }
    }

    companion object {
        private const val TAG = "LocationSettingViewMode"
    }

}