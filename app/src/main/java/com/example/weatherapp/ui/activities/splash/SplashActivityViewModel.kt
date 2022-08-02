package com.example.weatherapp.ui.activities.splash

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.R
import com.example.weatherapp.application.WeatherApplication
import com.example.weatherapp.constants.Constants.LOCATION_REQUEST_CHECK_SETTINGS
import com.example.weatherapp.customData.models.openWeatherModels.OneCallModel
import com.example.weatherapp.customData.models.room.Locations
import com.example.weatherapp.datamanager.DataManager
import com.example.weatherapp.source.local.prefs.PrefUtils
import com.example.weatherapp.ui.activities.main.MainActivity
import com.example.weatherapp.ui.base.BaseViewModel
import com.example.weatherapp.utilites.CommonKeys
import com.example.weatherapp.utilites.CommonKeys.KEY_CITY
import com.example.weatherapp.utilites.CommonKeys.KEY_COUNTRY
import com.example.weatherapp.utilites.CommonKeys.KEY_DATA
import com.example.weatherapp.utilites.CommonKeys.KEY_LAT
import com.example.weatherapp.utilites.CommonKeys.KEY_LNG
import com.example.weatherapp.utilites.PermissionUtils
import com.example.weatherapp.utilites.PermissionUtils.neverAskAgainSelected
import com.example.weatherapp.utilites.Utilities.Companion.isNetworkAvailable
import java.io.IOException
import java.util.*


class SplashActivityViewModel :
    BaseViewModel() {

    var city: String? = null
    var state: String? = null
    var country: String? = null
    var lat: String? = null
    var lng: String? = null
    var address: String? = null

    var oneCallModel: OneCallModel? = null
    val retryDialogObserver = MutableLiveData<String>()
    val noDefaultLocationDialogObserver = MutableLiveData<Void?>()
    val buildLocationManagerObserver = MutableLiveData<Void?>()
    val locationPermissionObserver = MutableLiveData<Void?>()

    private fun addLocationDataToRoomDb(address: Address) {

        DataManager.instance.insertLocation(
            Locations(
                address.locality,
                address.adminArea,
                address.countryName,
                address.latitude.toString(),
                address.longitude.toString(),
                address.getAddressLine(0),
                true
            )
        )
    }


    fun loadLocationData(activity: Activity) {
        if (isNetworkAvailable(activity)) {
            if (PrefUtils.getString(activity, KEY_LAT) != null) {
                activity.finish()
                activity.startActivity(Intent(activity, MainActivity::class.java))
            } else {
                buildLocationManagerObserver.postValue(null)
            }
        } else {
            retryDialogObserver.postValue(activity.getString(R.string.check_internet_connection))
        }
    }

    fun handleActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LOCATION_REQUEST_CHECK_SETTINGS) {
            when (resultCode) {
                Activity.RESULT_OK -> locationPermissionObserver.postValue(null)
                Activity.RESULT_CANCELED -> retryDialogObserver.postValue(activity.getString(R.string.location_permission_denied))
                else -> {
                }
            }
        }
    }

    fun buildLocationManager(activity: Activity) {
        activity.intent.removeExtra(KEY_DATA)
        if (isNetworkAvailable(activity)) {
            buildLocationManagerObserver.postValue(null)
        } else {
            retryDialogObserver.postValue(activity.getString(R.string.check_internet_connection))
        }
    }

    fun handleRequestPermissionsResult(
        activity: Activity,
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PermissionUtils.PERMISSIONS_REQUEST) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("Permission", "Granted")
                locationPermissionObserver.postValue(null)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissions.size > 0) {
                    if (neverAskAgainSelected(activity, permissions[0])) {
                        goToSystemLocationSetting(activity)
                    } else {
                        Log.d("Permission", "Denied")
                        retryDialogObserver.postValue(activity.getString(R.string.location_permission_denied))
                    }
                }
            } else {
                retryDialogObserver.postValue(activity.getString(R.string.location_permission_denied))
            }
        }
    }

    private fun goToSystemLocationSetting(activity: Activity) {
        val alertDialog = AlertDialog.Builder(activity).create()
        alertDialog.setTitle(activity.getString(R.string.alert))
        alertDialog.setMessage(activity.getString(R.string.enable_location))
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, activity.getString(R.string.goto_Setting)
        ) { dialog: DialogInterface, which: Int ->
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            activity.startActivity(intent)
            dialog.dismiss()
        }
        alertDialog.show()
    }

    fun handleLocationChanged(activity: Activity, pLocation: Location?) {
        if (pLocation != null) {
            var address:Address? =null
            val geocoder = Geocoder(activity, Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(
                    pLocation.latitude.toDouble(), pLocation.longitude.toDouble(), 1
                )
                address=addresses[0]
                addLocationDataToRoomDb(address)

            } catch (e: IOException) {
                e.printStackTrace()
            }

            lat = pLocation.latitude.toString()
            lng = pLocation.longitude.toString()
            PrefUtils.setString(
                WeatherApplication.getContext(),
                KEY_LAT,
                pLocation.latitude.toString()
            )
            PrefUtils.setString(
                WeatherApplication.getContext(),
                KEY_LNG,
                pLocation.longitude.toString()
            )
            PrefUtils.setString(
                WeatherApplication.getContext(),
                KEY_CITY,
               address?.locality
            )
            PrefUtils.setString(
                WeatherApplication.getContext(),
                KEY_COUNTRY,
                address?.countryName
            )
            PrefUtils.setString(
                WeatherApplication.getContext(),
                CommonKeys.KEY_UPDATED_LAT,
                pLocation.latitude.toString()
            )
            PrefUtils.setString(
                WeatherApplication.getContext(),
                CommonKeys.KEY_UPDATED_LNG,
                pLocation.longitude.toString()
            )
            activity.finish()
            activity.startActivity(Intent(activity, MainActivity::class.java))
            Log.d(TAG, pLocation.toString())

        } else {
            retryDialogObserver.postValue(activity.getString(R.string.location_permission_denied))
        }
    }


    companion object {
        private const val TAG = "SplashActivityViewModel"
    }

}