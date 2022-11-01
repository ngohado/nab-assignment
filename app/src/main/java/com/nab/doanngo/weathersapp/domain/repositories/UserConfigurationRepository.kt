package com.nab.doanngo.weathersapp.domain.repositories

import com.nab.doanngo.weathersapp.domain.models.dtos.TemperatureUnit

/**
 * Repository to get user's config for application.
 */
interface UserConfigurationRepository {
    /**
     * Get user's temperature unit config.
     */
    suspend fun getTemperatureUnit(): TemperatureUnit

    /**
     * Get user's number of next days forecast config.
     *
     * @return Number of next day forecast.
     */
    suspend fun getNumberOfForecastDay(): Int
}
