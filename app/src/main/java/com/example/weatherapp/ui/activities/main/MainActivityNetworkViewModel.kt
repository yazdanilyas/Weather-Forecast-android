package com.example.weatherapp.ui.activities.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.R
import com.example.weatherapp.application.WeatherApplication
import com.example.weatherapp.customData.models.ApiResponse
import com.example.weatherapp.customData.models.openWeatherModels.OneCallModel
import com.example.weatherapp.datamanager.DataManager
import com.example.weatherapp.source.remote.rxjava.CustomError
import com.example.weatherapp.source.remote.rxjava.CustomObserver
import com.example.weatherapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainActivityNetworkViewModel @Inject constructor():
   BaseViewModel() {
    var openWeatherUpdatesResponse = MutableLiveData<ApiResponse>()

    fun getOpenWeatherUpdatesApiResponse(lat: Double?, lng: Double?) {
        DataManager.instance.getOpenWeatherUpdatesResponse(lat, lng)
            .doOnSubscribe { openWeatherUpdatesResponse.postValue(ApiResponse.loading()) }
            ?.subscribe(object : CustomObserver<Response<OneCallModel?>?>() {
                override fun onSuccess(pResponse: Response<OneCallModel?>?) {
                    Log.d("TAG", "onSuccessTop: ${pResponse?.body()}")
                    if (pResponse?.body() != null) {
                        if (pResponse.isSuccessful) {
                            openWeatherUpdatesResponse.postValue( ApiResponse.success(pResponse.body()))
                        } else if (pResponse.code() == 401) {
                            openWeatherUpdatesResponse.postValue(
                                ApiResponse.error(
                                    CustomError(
                                        WeatherApplication.getContext()
                                            .getString(R.string.something_wrong), pResponse.code()
                                    )
                                )
                            )
                        } else {
                            openWeatherUpdatesResponse.postValue(
                                ApiResponse.error(
                                    CustomError(
                                        pResponse.message(),
                                        pResponse.code()
                                    )
                                )
                            )
                        }
                    } else {
                        Log.d("TAG", "on body null: ")
                    }
                }

                override fun onError(e: Throwable, isInternetError: Boolean, error: CustomError?) {
                    Log.d("TAG", "on body null: ")
                    openWeatherUpdatesResponse.postValue(
                        ApiResponse.error(
                            error?.code?.let { error_code ->
                                CustomError(
                                    Objects.requireNonNull(error).message.toString(), error_code
                                )
                            }

                        )
                    )
                }

                override fun onRequestComplete() {
                    openWeatherUpdatesResponse.postValue(ApiResponse.complete())
                }

            })
    }

}