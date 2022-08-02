package com.example.weatherapp.source.remote.rxjava

import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

abstract class CustomObserver<T> : Observer<T> {
    override fun onSubscribe(d: Disposable) {
        DisposableManager.add(d)
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        try {
            Log.e(TAG, "onError: ", e)
//            if (e is HttpException) {
//                onError(e, false, getErrorMessage(e))
//            } else if (e is ProtocolException) {
//                // TODO: rename all it variable to property name
//                onError(e, false, e.message?.let { error ->
//                    CustomError(error, 416)
//                })
//            } else onError(e, e !is Exception, e.message?.let { error ->
//                CustomError(error, 500)
//            })
        } catch (ex: Exception) {
            onError(e, true, e.message?.let { error ->
                CustomError(error, 500)
            })
        }
    }

    override fun onComplete() {
        Log.d(TAG, "onComplete: command complete")
      //  onRequestComplete()
    }

    private fun getErrorMessage(exception: HttpException): CustomError {
        return try {
            CustomError(exception.message.toString(), exception.code())
        } catch (e: Exception) {
            CustomError(e.message.toString(), exception.code())
        }
    }

    abstract fun onSuccess(t: T)
    abstract fun onError(e: Throwable, isInternetError: Boolean, error: CustomError?)
    abstract fun onRequestComplete()

    companion object {
        private const val TAG = "CustomObserver"
    }
}