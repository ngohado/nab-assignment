package com.nab.doanngo.weathersapp.data.repositories

import com.nab.doanngo.weathersapp.domain.models.dtos.TemperatureUnit
import com.nab.doanngo.weathersapp.domain.repositories.UserConfigurationRepository
import javax.inject.Inject

class UserConfigurationRepositoryImpl @Inject constructor() : UserConfigurationRepository {
    override suspend fun getTemperatureUnit(): TemperatureUnit {
        // TODO: Get from local storage
        return TemperatureUnit.Celsius
    }

    override suspend fun getNumberOfForecastDay(): Int {
        // TODO: Get from local storage
        return 7
    }
}
