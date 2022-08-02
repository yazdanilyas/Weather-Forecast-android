package com.example.weatherapp.customData.interfaces

import android.content.Context

interface CustomOnClickListener {
    fun onCustomClick(context:Context,data: Any)
    fun onDeleteButtonClick(context:Context,data: Any)

}