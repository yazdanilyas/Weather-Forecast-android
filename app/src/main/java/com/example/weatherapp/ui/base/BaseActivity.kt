package com.example.weatherapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    protected fun setUpActionBar(toolbar: Toolbar, pTitle: String) {
//        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.title = pTitle
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }
}