package com.example.weatherapp.source.remote.retrofit

import android.content.Context
import com.example.weatherapp.constants.Constants
import com.example.weatherapp.constants.Constants.PLACES_BASE_URL
import com.example.weatherapp.customData.enum.NetworkCallType
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val client = OkHttpClient.Builder()
        .addInterceptor(DataInterceptor())
        .build()
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofitObject: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.WEATHER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()

    private val mPlacesCall: Retrofit = Retrofit.Builder()
        .baseUrl(PLACES_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()


    fun get(pType: NetworkCallType?, pContext: Context?): ApiResponse {
        when (pType) {
            NetworkCallType.OPEN_WEATHER_API ->
                return retrofitObject.create(
                    ApiResponse::class.java
                )

            NetworkCallType.PLACES -> return mPlacesCall.create(
                ApiResponse::class.java
            )

            else -> {

            }
        }
        return retrofitObject.create(
            ApiResponse::class.java
        )

    }

}
