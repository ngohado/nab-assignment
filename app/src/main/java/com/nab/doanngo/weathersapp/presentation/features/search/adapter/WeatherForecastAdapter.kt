package com.nab.doanngo.weathersapp.presentation.features.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nab.doanngo.weathersapp.R
import com.nab.doanngo.weathersapp.databinding.ItemWeatherForecastDayBinding
import com.nab.doanngo.weathersapp.presentation.features.search.CityWeatherSearchDisplayModel
import com.nab.doanngo.weathersapp.presentation.features.search.CityWeatherSearchDisplayModel.WeatherForecastDay.Companion.DIFF
import com.nab.doanngo.weathersapp.presentation.utils.percentageFormat
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherForecastAdapter :
    ListAdapter<CityWeatherSearchDisplayModel.WeatherForecastDay, WeatherForecastAdapter.DayViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder(
            binding = ItemWeatherForecastDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    class DayViewHolder(
        private val binding: ItemWeatherForecastDayBinding
    ) : ViewHolder(binding.root) {
        private val dateFormat = SimpleDateFormat(
            itemView.context.getString(R.string.item_weather_forecast_day_date_format),
            Locale.getDefault()
        )

        fun bindData(data: CityWeatherSearchDisplayModel.WeatherForecastDay) {
            val context = itemView.context
            binding.apply {
                tvDate.text = context.getString(
                    R.string.item_weather_forecast_day_date_statement,
                    dateFormat.format(data.date)
                )

                tvTemperature.text = context.getString(
                    R.string.item_weather_forecast_day_temperature_statement,
                    data.temperatureFormatted
                )

                tvPressure.text = context.getString(
                    R.string.item_weather_forecast_day_pressure_statement,
                    data.pressure.toString()
                )

                tvHumidity.text = context.getString(
                    R.string.item_weather_forecast_day_humidity_statement,
                    data.humidity.toString().percentageFormat()
                )

                tvDescription.text = context.getString(
                    R.string.item_weather_forecast_day_description_statement,
                    data.description ?: context.getString(R.string.all_unknown)
                )
            }
        }
    }
}
