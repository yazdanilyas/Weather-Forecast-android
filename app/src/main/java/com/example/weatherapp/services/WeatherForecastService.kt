package com.example.weatherapp.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.weatherapp.R
import com.example.weatherapp.customData.models.openWeatherModels.OneCallModel
import com.example.weatherapp.datamanager.DataManager
import com.example.weatherapp.source.local.prefs.PrefUtils
import com.example.weatherapp.ui.activities.main.MainActivity
import com.example.weatherapp.utilites.CommonKeys
import com.example.weatherapp.utilites.Utilities

class WeatherForecastService : Service() {
    private val notificationChannelId = "channel_id"

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()
    }

    @SuppressLint("CheckResult")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        DataManager.instance.getOpenWeatherUpdatesResponse(
            PrefUtils.getString(this, CommonKeys.KEY_LAT)
                ?.toDouble(), PrefUtils.getString(this, CommonKeys.KEY_LNG)?.toDouble()
        ).doOnSubscribe {}.subscribe() {
            createNotification(this@WeatherForecastService, it?.body())
        }
        return START_NOT_STICKY
    }


    private fun createNotification(context: Context, responseBody: OneCallModel?) {

        val currentTemp = Utilities.convertTemperatureToDegree(responseBody?.current)
        val notificationId = 1
        val channelName = "WeatherChannel"
        val notificationChannel: NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                notificationChannelId,
                channelName,
                NotificationManager.IMPORTANCE_MIN
            )
            val notificationManager =
                (context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val mainIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0, mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(
                getString(R.string.today_weather) + " ${
                    PrefUtils.getString(
                        this@WeatherForecastService,
                        CommonKeys.KEY_CITY
                    )
                }"
            )
            .setContentText(getString(R.string.current_temp) + " $currentTemp")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_location)
            .setPriority(NotificationManager.IMPORTANCE_MIN)

        with(NotificationManagerCompat.from(context)) {
            notify(11, notificationBuilder.build())
            stopSelf()
        }
    }
}