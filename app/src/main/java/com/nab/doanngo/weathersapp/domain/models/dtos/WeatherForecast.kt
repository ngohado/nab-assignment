package com.nab.doanngo.weathersapp.domain.models.dtos

import java.util.Date

data class WeatherForecast(
    val clouds: Int,
    val deg: Int,
    val forecastDate: Date,
    val temperatureAverage: Double,
    val gust: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val speed: Double,
    val sunriseTime: Date,
    val sunsetTime: Date,
    val temperatureUnit: TemperatureUnit
)
