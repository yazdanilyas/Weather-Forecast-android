package com.example.weatherapp.constants

object Constants {
    const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/3.0/"
    const val DATABASE_LOCATION = "location_db"
    const val TABLE_LOCATION = "location_table"
    const val COLUMN_LAT = "column_lat"
    const val COLUMN_LNG = "column_lng"
    const val COLUMN_ADDRESS = "column_address"
    const val DEFAULT_UNIT_VALUE = "us"
    const val IMPERIAL = "imperial"
    const val METRIC = "metric"
    const val OPEN_WEATHER_API_KEY = "d68ae0c08850d99cc154ea6a9d29750c"
    const val PLACES_BASE_URL = "https://maps.google.com/maps/api/geocode/"

    @JvmField
    var GPS_INTERVIAL = 1000 * 5

    @JvmField
    var GPS_FASTEST_INTERVIAL = 1000
    const val LOCATION_REQUEST_CHECK_SETTINGS = 102


}