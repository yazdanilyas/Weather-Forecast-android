package com.example.weatherapp.ui.activities.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.constants.Constants
import com.example.weatherapp.databinding.ActivitySettingBinding
import com.example.weatherapp.source.local.prefs.PrefUtils
import com.example.weatherapp.utilites.CommonKeys

class SettingActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setRadioButtonsState()
        setListeners()
    }

    private fun setRadioButtonsState() {
        val unit = PrefUtils.getString(
            this,
            CommonKeys.KEY_UNIT,
            Constants.IMPERIAL
        )
        if (unit == Constants.IMPERIAL) {
            mBinding.fahrenheitRB.isChecked = true
        } else {
            mBinding.celsiusRB.isChecked = true
        }
    }

    private fun setListeners() {
        mBinding.celsiusRB.setOnCheckedChangeListener { p0, isChecked ->
            if (isChecked) {
                PrefUtils.setString(this@SettingActivity, CommonKeys.KEY_UNIT, Constants.METRIC)
                finish()
            }
        }
        mBinding.fahrenheitRB.setOnCheckedChangeListener { p0, isChecked ->
            if (isChecked) {
                PrefUtils.setString(this@SettingActivity, CommonKeys.KEY_UNIT, Constants.IMPERIAL)
                finish()
            }
        }
    }
}