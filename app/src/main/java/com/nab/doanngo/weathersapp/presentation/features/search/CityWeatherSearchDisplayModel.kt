package com.nab.doanngo.weathersapp.presentation.features.search

import androidx.recyclerview.widget.DiffUtil
import com.nab.doanngo.weathersapp.domain.models.dtos.TemperatureUnit
import com.nab.doanngo.weathersapp.domain.models.dtos.format
import java.util.Date

object CityWeatherSearchDisplayModel {
    const val CITY_NAME_KEY_SEARCH_MIN_CHARACTER = 3

    data class WeatherForecastDay(
        val date: Date,
        val temperature: Double,
        val temperatureUnit: TemperatureUnit,
        val pressure: Int,
        val humidity: Int,
        val description: String?
    ) {
        val id: Long
            get() = date.time

        val temperatureFormatted: String
            get() = temperatureUnit.format(temperature)

        companion object {
            val DIFF = object : DiffUtil.ItemCallback<WeatherForecastDay>() {
                override fun areItemsTheSame(
                    oldItem: WeatherForecastDay,
                    newItem: WeatherForecastDay
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: WeatherForecastDay,
                    newItem: WeatherForecastDay
                ): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}
