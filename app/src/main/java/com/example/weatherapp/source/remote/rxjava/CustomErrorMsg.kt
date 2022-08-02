package com.example.weatherapp.source.remote.rxjava

import android.content.Context
import android.widget.Toast

/**
 * This class is used to show error message according to error code @link CustomError.class
 */
object CustomErrorMsg {
    /**
     * This static method filter result according to parameters and show message
     *
     * @param context         context of calling class
     * @param e               <Throwable> exception
     * @param isInternetError boolean is internet error occur
     * @param error           <CustomError> Custom error message with code {@see CustomError}
    </CustomError></Throwable> */
    fun show(context: Context?, e: Throwable, isInternetError: Boolean, error: CustomError?) {
        if (isInternetError) {
            Toast.makeText(context, "Internet connection error occurred", Toast.LENGTH_LONG).show()
        } else if (error != null) {
            when (error.code) {
                400, 401, 404, 409, 500 -> Toast.makeText(context, error.message, Toast.LENGTH_LONG)
                    .show() //show message
            }
        }
        e.printStackTrace()
    }
}