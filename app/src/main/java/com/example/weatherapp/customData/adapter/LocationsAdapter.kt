package com.example.weatherapp.customData.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.customData.interfaces.CustomOnClickListener
import com.example.weatherapp.customData.models.room.Locations
import com.example.weatherapp.databinding.LocationItemLayoutBinding

class LocationsAdapter(
    private val context: Context,
    private val mLocationsList: MutableList<Locations>,
   private val customClickListener: CustomOnClickListener
) : RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>() {
    private lateinit var binding: LocationItemLayoutBinding

    class LocationsViewHolder(itemView: LocationItemLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        binding =
            LocationItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LocationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        binding.obj=mLocationsList[position]
        binding.root.setOnClickListener {
            customClickListener.onCustomClick(context,mLocationsList[position])
        }
        binding.addressDeleteButton.setOnClickListener {
            customClickListener.onDeleteButtonClick(context,mLocationsList[position])
        }
    }

    override fun getItemCount(): Int {
        return  mLocationsList.size
    }
}
