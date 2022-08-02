package com.example.weatherapp.source.remote.rxjava

import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

object DisposableManager {
    private const val TAG = "DisposableManager"
    private lateinit var mCompositeDisposable: CompositeDisposable
    fun add(disposable: Disposable?) {
        try {
            if (disposable != null) {
                compositeDisposable.add(disposable)
            }
        } catch (e: Exception) {
            Log.e(TAG, "add: ", e)
        }
    }

    @JvmStatic
    fun dispose() {
        try {
            compositeDisposable.dispose()
        } catch (e: Exception) {
            Log.e(TAG, "dispose: ", e)
        }
    }
    private val compositeDisposable: CompositeDisposable
        get() {
            mCompositeDisposable = CompositeDisposable()
            return mCompositeDisposable
        }
}