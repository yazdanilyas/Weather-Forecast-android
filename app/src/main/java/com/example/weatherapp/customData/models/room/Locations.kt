package com.example.weatherapp.customData.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Locations(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "cityName")
    var cityName: String? = null,

    @ColumnInfo(name = "stateName")
    var stateName: String? = null,

    @ColumnInfo(name = "countryName")
    var countryName: String? = null,

    @ColumnInfo(name = "latitude")
    var latitude: String? = null,

    @ColumnInfo(name = "longitude")
    var longitude: String? = null,

    @ColumnInfo(name = "address")
    var address: String? = null,

    @ColumnInfo(name = "isCurrent")
    var isCurrent: Boolean? = null
) {
    constructor(
        pCityName: String?,
        pStateName: String?,
        pCountryName: String?,
        pLatitude: String?,
        pLongitude: String?,
        pAddress: String?,
        pIsCurrent: Boolean?
    ) : this() {
        cityName = pCityName
        stateName = pStateName
        countryName = pCountryName
        latitude = pLatitude
        longitude = pLongitude
        address = pAddress
        isCurrent = pIsCurrent
    }

    override fun toString(): String {
        return "Locations(id=$id, cityName=$cityName, stateName=$stateName, countryName=$countryName, latitude=$latitude, longitude=$longitude, address=$address)"
    }

}