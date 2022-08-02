package com.example.weatherapp.customData.models

import com.example.weatherapp.customData.enum.NetworkStatus
import com.example.weatherapp.source.remote.rxjava.CustomError


data class ApiResponse(val status: NetworkStatus, var t: Any?, val error: CustomError?) {

    companion object {
        @JvmStatic
        fun loading(): ApiResponse {
            return ApiResponse(NetworkStatus.LOADING, null, null)
        }

        @JvmStatic
        fun success(t: Any?): ApiResponse {
            return ApiResponse(NetworkStatus.SUCCESS, t, null)
        }

        @JvmStatic
        fun error(error: CustomError?): ApiResponse {
            return ApiResponse(NetworkStatus.ERROR, null, error)
        }

        @JvmStatic
        fun complete(): ApiResponse {
            return ApiResponse(NetworkStatus.COMPLETED, null, null)
        }

        fun expire(error: CustomError?): ApiResponse {
            return ApiResponse(NetworkStatus.EXPIRE, null, error)
        }
    }

    override fun toString(): String {
        return "ApiResponse(status=$status, t=$t, error=$error)"
    }
}