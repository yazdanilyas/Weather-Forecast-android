package com.example.weatherapp.services.locationManager

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.weatherapp.R
import com.example.weatherapp.application.WeatherApplication
import com.example.weatherapp.constants.Constants.GPS_FASTEST_INTERVIAL
import com.example.weatherapp.constants.Constants.GPS_INTERVIAL
import com.example.weatherapp.constants.Constants.LOCATION_REQUEST_CHECK_SETTINGS
import com.example.weatherapp.utilites.PermissionUtils
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import javax.inject.Inject

class LocationManagerClient @Inject constructor() {
    private val mContext: Context
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mActivity: Activity
    private lateinit var mLocationListener: LocationListener
    private val mLocationManager: LocationManager?
    fun setActivity(pActivity: Activity) {
        mActivity = pActivity
    }

    fun setLocationListener(pLocationListener: LocationListener) {
        mLocationListener = pLocationListener
    }

    fun setBuild() {
        Log.d(TAG, "setBuild: Called")
        if (mActivity == null || mLocationListener == null) {
            throw NullPointerException("Initialize Activity and listener First then call this method")
        } else {
            Log.d(TAG, "setBuild: mActivity & mLocationListener not null")
            //   mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);
            if (checkPlayServices()) {
                locationPermission
            } else {
//                  mLocationListener.onLocationChanged(null)
            }
        }
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = GPS_INTERVIAL.toLong()
        mLocationRequest.fastestInterval = GPS_FASTEST_INTERVIAL.toLong()
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        mLocationRequest.smallestDisplacement = 1000F
    }

    private fun checkPlayServices(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(mContext)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Toast.makeText(
                    mContext,
                    mContext.resources.getString(R.string.no_google_play_service),
                    Toast.LENGTH_LONG
                ).show()
            }
            return false
        }
        return true
    }

    val locationPermission: Unit
        get() {
            if (ActivityCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    mActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PermissionUtils.PERMISSIONS_REQUEST
                )
                Log.i(TAG, "getLocationPermission: check Self permission ")
                PermissionUtils.setShouldShowStatus(
                    mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } else {
                Log.i(TAG, "getLocationPermission: Call for Location setting ....")
                checkLocationSetting()
            }
        }

    private fun checkLocationSetting() {
        createLocationRequest()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest)
        val client = LocationServices.getSettingsClient(mContext)
        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener(mActivity) {
            Log.i(TAG, "onSuccess: GPS location is fine")

            // getLastKnownlocation();
            lastLocation
        }
        task.addOnFailureListener(mActivity) { e ->
            if (e is ResolvableApiException) {
                Log.e(TAG, "onFailure: GPS Setting is not fine ")
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(
                        mActivity,
                        LOCATION_REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: SendIntentException) {
                    // Ignore the error.
                    Log.e(TAG, "onFailure: ", sendEx)
                }
            }
        }
    }

    //provider name is unnecessary
    //your coords of course
    @get:SuppressLint("MissingPermission")
    private val lastLocation: Unit
        private get() {
            val locationListener: LocationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    Log.i("location", location.latitude.toString() + "")
                    mLocationListener.onLocationChanged(location)
                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }
            if (mLocationManager != null && mLocationManager.allProviders.contains("network")) {
                mLocationManager.requestSingleUpdate(
                    LocationManager.NETWORK_PROVIDER,
                    locationListener,
                    null
                )
            } else {
                val targetLocation = Location("") //provider name is unnecessary
                targetLocation.latitude = 31.5006307 //your coords of course
                targetLocation.longitude = 74.3219958
                mLocationListener.onLocationChanged(targetLocation)
                Toast.makeText(mContext, "No GPS Available go for hardcoded", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    companion object {
        private const val TAG = "LocationManagerClient"
    }

    init {
        mContext = WeatherApplication.getContext()
        mLocationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}