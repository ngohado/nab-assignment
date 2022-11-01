package com.nab.doanngo.weathersapp.data.datasources.remote

import com.nab.doanngo.weathersapp.data.datasources.remote.entities.CityWeatherResponse
import com.nab.doanngo.weathersapp.data.datasources.remote.entities.toAPIRequestValue
import com.nab.doanngo.weathersapp.domain.models.dtos.TemperatureUnit
import javax.inject.Inject

/**
 * Get weather information from API.
 */
class WeatherRemoteDataSource @Inject constructor(
    private val weatherAPIService: WeatherAPIService
) {
    /**
     * Get weather information of [city] name, in next [forecastDays] days.
     *
     * @return City weather information response from server.
     */
    suspend fun getCityWeatherForecast(
        city: String,
        forecastDays: Int,
        temperatureUnit: TemperatureUnit
    ): CityWeatherResponse {
        return weatherAPIService.getCityWeatherForecast(
            city = city,
            forecastDays = forecastDays,
            temperatureUnit = temperatureUnit.toAPIRequestValue()
        )
    }
}
