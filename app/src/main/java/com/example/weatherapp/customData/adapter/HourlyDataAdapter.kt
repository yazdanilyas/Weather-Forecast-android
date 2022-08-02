package com.example.weatherapp.customData.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.customData.models.openWeatherModels.Hourly
import com.example.weatherapp.databinding.HourlyListItemBinding
import com.example.weatherapp.ui.activities.main.MainActivity

class HourlyDataAdapter(val context: MainActivity, private val hourlyForecastData: MutableList<Hourly>?) :
    RecyclerView.Adapter<HourlyDataAdapter.HourlyViewHolder>() {
    private lateinit var binding: HourlyListItemBinding

    class HourlyViewHolder(itemView: HourlyListItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        binding =
            HourlyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HourlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        binding.hourly = hourlyForecastData?.get(position)
    }

    override fun getItemCount(): Int {
      return  hourlyForecastData!!.size
    }
}
