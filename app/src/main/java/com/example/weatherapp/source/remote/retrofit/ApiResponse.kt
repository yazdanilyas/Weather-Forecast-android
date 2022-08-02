package com.example.weatherapp.source.remote.retrofit

import com.example.weatherapp.customData.models.addressModels.PlacesResult
import com.example.weatherapp.customData.models.openWeatherModels.OneCallModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiResponse {

    // open weather api
    @GET("onecall")
    fun getOpenWeatherUpdates(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("units") units: String?,
        @Query("appid") app_id: String?
    ): Observable<Response<OneCallModel?>?>

    @GET("json?sensor=false&key=AIzaSyBEMPqgoqY67Vnptwsed6_6QsIGTVHQEaU")
    fun getPlaceAddressApi(@Query("address") coOrdinate: String?): Observable<Response<PlacesResult?>?>?
}