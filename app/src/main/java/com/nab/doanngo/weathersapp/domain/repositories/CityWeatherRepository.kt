package com.nab.doanngo.weathersapp.domain.repositories

import com.nab.doanngo.weathersapp.domain.models.dtos.CityWeatherInformation

/**
 * Repository to get city weather information.
 */
interface CityWeatherRepository {
    /**
     * Get weather information of provided [city] name.
     *
     * @return The city summary information, and weather forecast in next days.
     */
    suspend fun getWeatherOfCity(city: String): CityWeatherInformation
}
