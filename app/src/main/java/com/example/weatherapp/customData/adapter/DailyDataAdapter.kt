package com.example.weatherapp.customData.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.customData.models.openWeatherModels.Daily
import com.example.weatherapp.databinding.DailyListItemBinding
import com.example.weatherapp.ui.activities.main.MainActivity

class DailyDataAdapter(val context: MainActivity, private val dailyForecastData: MutableList<Daily>?) :
    RecyclerView.Adapter<DailyDataAdapter.DailyViewHolder>() {
    private lateinit var binding: DailyListItemBinding

    class DailyViewHolder(itemView: DailyListItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        binding =
            DailyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DailyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        binding.daily = dailyForecastData?.get(position)
    }

    override fun getItemCount(): Int {
      return  dailyForecastData!!.size
    }
}
