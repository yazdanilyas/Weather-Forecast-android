package com.example.weatherapp.source.remote.rxjava

object ErrorUtils {
   /* @JvmStatic
    fun parseVideoApiError(response: Response<VideoResponse?>): VideoResponse {
        val converter = ApiClient.retrofitObject
            .responseBodyConverter<VideoResponse>(VideoResponse::class.java, arrayOfNulls(0))
        var errorResponse = VideoResponse()

        errorResponse = try {
            converter.convert(Objects.requireNonNull(response.errorBody())) ?: VideoResponse(
                null,
                CustomError(
                    WeatherApplication.getContext().getString(R.string.something_wrong), -1
                )
            )
        } catch (e: Exception) {

            // TODO: rename all it variable to property name

            return VideoResponse(null, e.message?.let { errorMessage ->
                CustomError(errorMessage, response.code())
            })
        }

        return errorResponse
    }*/
}