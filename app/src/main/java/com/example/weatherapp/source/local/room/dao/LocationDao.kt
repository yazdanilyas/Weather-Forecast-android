package com.example.weatherapp.source.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.customData.models.room.Locations

@Dao
abstract class LocationDao {
    @get:Query("Select * from locations")
    abstract val all: LiveData<List<Locations>>

    @Insert
    abstract fun insertNewLocation(vararg pLocations: Locations)

    @Delete
    abstract fun removeLocation(vararg pLocations: Locations)

    @Query("Select * from locations where latitude =:lat AND longitude=:lng")
    abstract fun existLocation(lat: String, lng: String): Locations

    @Query("UPDATE locations SET cityName=:pCityName AND stateName=:pStateName AND countryName=:pCountryName AND latitude=:pLatitude AND longitude=:pLongitude AND address=:pAddress AND isCurrent=:pIsCurrent WHERE id=1")
    abstract fun updateCurrentLocation(
        pCityName: String?,
        pStateName: String?,
        pCountryName: String?,
        pLatitude: String?,
        pLongitude: String?,
        pAddress: String?,
        pIsCurrent: Boolean?
    )

}