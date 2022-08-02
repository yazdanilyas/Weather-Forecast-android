package com.example.weatherapp.datamanager

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.weatherapp.application.WeatherApplication
import com.example.weatherapp.constants.Constants
import com.example.weatherapp.customData.enum.NetworkCallType
import com.example.weatherapp.customData.models.addressModels.PlacesResult
import com.example.weatherapp.customData.models.openWeatherModels.OneCallModel
import com.example.weatherapp.customData.models.room.Locations
import com.example.weatherapp.source.local.prefs.PrefUtils
import com.example.weatherapp.source.local.room.database.LocationsDatabase
import com.example.weatherapp.source.remote.retrofit.ApiClient
import com.example.weatherapp.utilites.CommonKeys
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class DataManager  @Inject constructor() : DataManagerImp {
    //Local calls
    override fun insertLocation(pLocations: Locations) {
        val mLocation =
            pLocations.latitude?.let { latitude ->
                pLocations.longitude?.let { longitude ->
                    LocationsDatabase.getDatabaseInstance(WeatherApplication.getContext())
                        .locationDao()
                        .existLocation(
                            latitude, longitude
                        )
                }
            }
        if (mLocation == null) {
            LocationsDatabase.getDatabaseInstance(WeatherApplication.getContext()).locationDao()
                .insertNewLocation(pLocations)
            Log.d(TAG, "insertLocation: New Location Inserted")
        }
    }

    override fun updateCurrentLocation(pLocations: Locations) {
        if (pLocations.isCurrent == true) {
            LocationsDatabase.getDatabaseInstance(WeatherApplication.getContext()).locationDao()
                .updateCurrentLocation(
                    pLocations.cityName,
                    pLocations.stateName,
                    pLocations.countryName,
                    pLocations.latitude,
                    pLocations.longitude,
                    pLocations.address,
                    pLocations.isCurrent
                )
            Log.d(TAG, "updateCurrentLocation: Location Updated")
        }
    }

    /*
     * get location object based on chosen location lat lng .
     */
    override fun getLocation(lat: String, lng: String): Locations {
        return LocationsDatabase.getDatabaseInstance(WeatherApplication.getContext()).locationDao()
            .existLocation(lat, lng)
    }

    override fun deleteLocation(pLocations: Locations) {
        LocationsDatabase.getDatabaseInstance(WeatherApplication.getContext()).locationDao()
            .removeLocation(pLocations)
    }

    override val allLocationsObserver: LiveData<List<Locations>>
        get() = LocationsDatabase.getDatabaseInstance(WeatherApplication.getContext())
            .locationDao().all


    override fun getPlaceAddressResponse(coOrdinate: String?): Observable<Response<PlacesResult?>?>? {
        return ApiClient.get(NetworkCallType.PLACES, WeatherApplication.getContext())
            .getPlaceAddressApi(coOrdinate)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
    }

    //network
    override fun getOpenWeatherUpdatesResponse(
        lat: Double?,
        lng: Double?
    ): Observable<Response<OneCallModel?>?> {

        val unit = WeatherApplication.context.let { context ->
            PrefUtils.getString(
                context,
                CommonKeys.KEY_UNIT,
                Constants.IMPERIAL
            )
        }
        val pUnit: String?
        if (unit == Constants.IMPERIAL) {
            pUnit = Constants.IMPERIAL
        } else {
            pUnit = Constants.METRIC
        }

        return ApiClient.get(NetworkCallType.OPEN_WEATHER_API, WeatherApplication.getContext())
            .getOpenWeatherUpdates(
                lat,
                lng,
                pUnit,
                Constants.OPEN_WEATHER_API_KEY
            ).subscribeOn(Schedulers.io())
    }

    companion object {
        private const val TAG = "DataManager"

        @JvmStatic
        val instance: DataManager
            get() = DataManager()

        @JvmName("getInstance1")
        fun getInstance(): DataManager {
            return DataManager()
        }
    }
}