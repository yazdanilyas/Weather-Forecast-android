package com.example.weatherapp.ui.activities.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.databinding.ActivitySaplashBinding
import com.example.weatherapp.services.locationManager.LocationManagerClient
import com.example.weatherapp.utilites.DialogUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity(), LocationListener, DialogUtils.CallBack {
   @Inject lateinit var mLocationManagerClient: LocationManagerClient
    private val mViewModel: SplashActivityViewModel by viewModels()
    private lateinit var mDialogUtils: DialogUtils
    private lateinit var mBinding: ActivitySaplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySaplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mDialogUtils = DialogUtils(this)
        setListeners()
        setObserver()
        mViewModel.loadLocationData(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mViewModel.handleActivityResult(this@SplashActivity, requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mViewModel.handleRequestPermissionsResult(
            this@SplashActivity,
            requestCode,
            permissions,
            grantResults
        )
    }

    private fun setObserver() {
        //view based observers
        mViewModel.retryDialogObserver.observe(
            this
        ) { string: String? ->
            mDialogUtils.retryDialog(string, this@SplashActivity)
        }

        mViewModel.noDefaultLocationDialogObserver.observe(
            this
        ) { unused: Void? -> mDialogUtils.showAlertNoDefaultLocation(this@SplashActivity) }

        mViewModel.buildLocationManagerObserver.observe(
            this
        ) { unused: Void? -> mLocationManagerClient.setBuild() }

        mViewModel.locationPermissionObserver.observe(
            this
        ) { unused: Void? -> mLocationManagerClient.locationPermission }
    }

    override fun onLocationChanged(location: Location) {
        mViewModel.handleLocationChanged(this@SplashActivity, location)
    }

    private fun setListeners() {
        mLocationManagerClient.setActivity(this)
        mLocationManagerClient.setLocationListener(this)
    }

    override fun OnRetry() {
        mViewModel.loadLocationData(this)
    }

    override fun onNoDefaultLocationFound() {
        mViewModel.buildLocationManager(this@SplashActivity)
    }
}
