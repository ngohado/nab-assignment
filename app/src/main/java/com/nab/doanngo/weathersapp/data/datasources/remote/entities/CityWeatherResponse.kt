package com.nab.doanngo.weathersapp.data.datasources.remote.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityWeatherResponse(
    val city: CityEntity?,
    val cnt: Int?,
    @SerialName("cod")
    val cod: ResponseCode,
    val list: List<WeatherForecastEntity>?,
    val message: Double
)
