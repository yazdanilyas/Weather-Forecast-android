package com.example.weatherapp.ui.activities.locations

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.customData.adapter.LocationsAdapter
import com.example.weatherapp.customData.interfaces.CustomOnClickListener
import com.example.weatherapp.customData.models.room.Locations
import com.example.weatherapp.databinding.ActivityLocationSettingBinding
import com.example.weatherapp.datamanager.DataManager
import com.example.weatherapp.ui.base.BaseActivity
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LocationSettingActivity : BaseActivity() {
    private val PLACE_PICKER_REQUEST = 111
    private lateinit var mBinding: ActivityLocationSettingBinding
    private val mViewModel: LocationSettingViewModel by viewModels()
    private lateinit var mAdapter: LocationsAdapter


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private val customClickListener = object : CustomOnClickListener {
        override fun onCustomClick(context: Context, data: Any) {
            mViewModel.handleLocationClick(context as LocationSettingActivity, data)
        }

        override fun onDeleteButtonClick(context: Context, data: Any) {
            Log.d(TAG, "onDeleteButtonClick: ${(data as Locations).address}")
            DataManager.getInstance().deleteLocation(data as Locations)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        initView()
        setListeners()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PLACE_PICKER_REQUEST) {
            when (resultCode) {
                RESULT_OK -> {
                    if (data != null) {
                        mViewModel.handlePlacePickerOkResponse(this, data)
                    }
                    if (data != null) {
                        mViewModel.handlePlacePickerErrorResponse(data)
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> data?.let { data ->
                    mViewModel.handlePlacePickerErrorResponse(
                        data
                    )
                }
                RESULT_CANCELED -> Log.e(TAG, "onActivityResult: ")
            }
        }
    }

    private fun setListeners() {
        mBinding.addNewLocation.setOnClickListener { v: View? ->
            mViewModel.handleNewLocationClick(
                this@LocationSettingActivity
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {
        mViewModel.getLocationsObserver().observe(this) { pLocations: List<Locations> ->
            if (mViewModel.mLocationsList.size > 0)
                mViewModel.mLocationsList.clear()
            mViewModel.mLocationsList.addAll(pLocations)
            mAdapter.notifyDataSetChanged()
        }
        mViewModel.initializeAutoCompletePlace.observe(this) { unused: Void? ->
            initializeAutoCompletePlace()
        }


    }

    private fun initView() {
        mBinding = ActivityLocationSettingBinding.inflate(
            layoutInflater
        )
        setContentView(mBinding.root)
        setLocationsAdapter()
    }


    private fun setLocationsAdapter() {
        mAdapter =
            LocationsAdapter(
                this@LocationSettingActivity,
                mViewModel.mLocationsList,
                customClickListener
            )
        mBinding.locationsRecyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.locationsRecyclerView.adapter = mAdapter

    }

    private fun initializeAutoCompletePlace() {
        Places.initialize(
            getApplicationContext(),
            "AIzaSyBEMPqgoqY67Vnptwsed6_6QsIGTVHQEaU",
            Locale.US
        );
        val fields = Arrays.asList(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.LAT_LNG,
            Place.Field.ADDRESS
        )
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.FULLSCREEN, fields
        )
            .build(this)
        startActivityForResult(intent, PLACE_PICKER_REQUEST)
    }

    companion object {
        private const val TAG = "LocationSettingActivity"
    }
}