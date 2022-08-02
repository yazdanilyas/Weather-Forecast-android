package com.example.weatherapp.utilites

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.weatherapp.R

class DialogUtils(context: Context) {
    private val mContext: Context
    fun retryDialog(message: String?, pCallBack: CallBack) {
        val retryAlertDialog = AlertDialog.Builder(mContext).create()
        retryAlertDialog.setTitle(mContext.resources.getString(R.string.alert))
        retryAlertDialog.setCancelable(false)
        retryAlertDialog.setMessage(message)
        retryAlertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, mContext.resources.getString(R.string.retry)
        ) { dialog, which ->
            pCallBack.OnRetry()
            dialog.dismiss()
        }
        retryAlertDialog.show()
    }

    fun showAlertNoDefaultLocation(pCallBack: CallBack) {
        val noDefaultLocationAlertDialog = AlertDialog.Builder(mContext).create()
        noDefaultLocationAlertDialog.setTitle(mContext.resources.getString(R.string.alert))
        noDefaultLocationAlertDialog.setCancelable(false)
        noDefaultLocationAlertDialog.setMessage(mContext.resources.getString(R.string.noDefaultLocationMessage))
        noDefaultLocationAlertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, mContext.resources.getString(R.string.ok)
        ) { dialog, which ->
            pCallBack.onNoDefaultLocationFound()
            dialog.dismiss()
        }
        noDefaultLocationAlertDialog.show()
    }



    interface CallBack {
        fun OnRetry()
        fun onNoDefaultLocationFound()
    }

    init {
        mContext = context
    }
}