package com.example.weatherapp.datamanager

import androidx.lifecycle.LiveData
import com.example.weatherapp.customData.models.addressModels.PlacesResult
import com.example.weatherapp.customData.models.openWeatherModels.OneCallModel
import com.example.weatherapp.customData.models.room.Locations
import io.reactivex.Observable
import retrofit2.Response

interface DataManagerImp {
    //local calls
    val allLocationsObserver: LiveData<List<Locations>>
    fun insertLocation(pLocations: Locations)

    fun updateCurrentLocation(pLocations: Locations)
    fun getLocation(lat: String, lng: String): Locations
    fun deleteLocation(pLocations: Locations)
    fun getOpenWeatherUpdatesResponse(
        lat: Double?,
        lng: Double?
    ): Observable<Response<OneCallModel?>?>
    fun getPlaceAddressResponse(coOrdinate: String?): Observable<Response<PlacesResult?>?>?
}