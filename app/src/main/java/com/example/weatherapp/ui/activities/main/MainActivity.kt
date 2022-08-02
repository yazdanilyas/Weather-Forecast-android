package com.example.weatherapp.ui.activities.main

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.customData.adapter.DailyDataAdapter
import com.example.weatherapp.customData.adapter.HourlyDataAdapter
import com.example.weatherapp.customData.enum.NetworkStatus
import com.example.weatherapp.customData.models.ApiResponse
import com.example.weatherapp.customData.models.openWeatherModels.OneCallModel
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.services.WeatherForecastService
import com.example.weatherapp.source.local.prefs.PrefUtils
import com.example.weatherapp.ui.activities.locations.LocationSettingActivity
import com.example.weatherapp.ui.activities.setting.SettingActivity
import com.example.weatherapp.utilites.CommonKeys
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainActivityNetworkViewModel: MainActivityNetworkViewModel by viewModels()
    private val mMainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var mBinding: ActivityMainBinding
    private var alarmMgr: AlarmManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initViews()
        setObservers()
        setDailyNotification()
    }

    @SuppressLint("SetTextI18n")
    override fun onRestart() {
        super.onRestart()
        mBinding.currentWeatherLayout.txtLocation.text = getCompleteLocation()
        mainActivityNetworkViewModel.getOpenWeatherUpdatesApiResponse(
            PrefUtils.getString(this, CommonKeys.KEY_LAT)
                ?.toDouble(), PrefUtils.getString(this, CommonKeys.KEY_LNG)?.toDouble()
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(this@MainActivity, SettingActivity::class.java))
            }
            R.id.locations -> {
                startActivity(Intent(applicationContext, LocationSettingActivity::class.java))
            }
        }
        return true
    }

    private fun setObservers() {
        mainActivityNetworkViewModel.openWeatherUpdatesResponse.observe(this) { apiResponse: ApiResponse ->
            when (apiResponse.status) {
                NetworkStatus.LOADING -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                    mBinding.contentWrapper.visibility = View.GONE
                }
                NetworkStatus.SUCCESS -> {
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.contentWrapper.visibility = View.VISIBLE
                    val oneCallModel = apiResponse.t as OneCallModel?
                    mMainActivityViewModel.oneCallResponse = oneCallModel
                    setCurrentWeather(oneCallModel)
                    handleWeatherResponse(oneCallModel)

                }
                NetworkStatus.ERROR -> {
                    mBinding.progressBar.visibility = View.GONE
                    Log.d("TAG", "forecast api response: Error...")
                }
                NetworkStatus.EXPIRE -> {
                    mBinding.progressBar.visibility = View.GONE
                }
                NetworkStatus.COMPLETED -> {
                    mBinding.progressBar.visibility = View.GONE
                }
            }

        }
    }


    private fun initViews() {
        mBinding.currentWeatherLayout.txtLocation.text = getCompleteLocation()
        mainActivityNetworkViewModel.getOpenWeatherUpdatesApiResponse(
            PrefUtils.getString(this, CommonKeys.KEY_LAT)
                ?.toDouble(), PrefUtils.getString(this, CommonKeys.KEY_LNG)?.toDouble()
        )
    }

    private fun getCompleteLocation(): String {
        val city = PrefUtils.getString(this, CommonKeys.KEY_CITY)
        val country = PrefUtils.getString(
            this,
            CommonKeys.KEY_COUNTRY
        )
        return "$city,$country"
    }

    private fun setDailyNotification() {
        alarmMgr = getSystemService(Context.ALARM_SERVICE) as? AlarmManager?
        val alarmIntent = Intent(this@MainActivity, WeatherForecastService::class.java)
        val pendingIntent = PendingIntent.getService(
            this,
            0,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        // trigger time
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 6
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
//        val time=System.currentTimeMillis()+(2000)
        //set alarm
        alarmMgr?.setRepeating(
            AlarmManager.RTC_WAKEUP,
           calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }


    private fun handleWeatherResponse(oneCallModel: OneCallModel?) {
        PrefUtils.setString(
            this, CommonKeys.KEY_TIMEZONE, mMainActivityViewModel.oneCallResponse?.timezone
        )
        //hourly adapter
        setHourlyAdapter(oneCallModel)
        //Daily adapter
        setDailyAdapter(oneCallModel)
    }

    private fun setDailyAdapter(oneCallModel: OneCallModel?) {
        mMainActivityViewModel.dailyForecastData?.clear()
        oneCallModel?.daily.let {
            if (it != null) {
                mMainActivityViewModel.dailyForecastData?.addAll(it)
            }
        }
        mMainActivityViewModel.dailyForecastData?.reverse()

        val mAdapter2 = DailyDataAdapter(this, mMainActivityViewModel.dailyForecastData)
        mBinding.rcDailyWeatherList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        mBinding.rcDailyWeatherList.adapter = mAdapter2
    }

    private fun setHourlyAdapter(oneCallModel: OneCallModel?) {
        mMainActivityViewModel.hourlyForecastData?.clear()
        oneCallModel?.hourly?.let {
            mMainActivityViewModel.hourlyForecastData?.addAll(it)
        }
        val mAdapter = HourlyDataAdapter(this, mMainActivityViewModel.hourlyForecastData)
        mBinding.rcHourlyWeatherList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        mBinding.rcHourlyWeatherList.adapter = mAdapter
    }

    private fun setCurrentWeather(current: OneCallModel?) {
        mBinding.currentWeatherLayout.oneCall = current
        mBinding.currentWeatherLayout.executePendingBindings()

    }


}