package com.nab.doanngo.weathersapp.domain.models.dtos

data class CityWeatherInformation(
    val city: City,
    val weatherNextDays: List<WeatherForecast>
)
