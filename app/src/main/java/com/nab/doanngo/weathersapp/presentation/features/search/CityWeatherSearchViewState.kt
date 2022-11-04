package com.nab.doanngo.weathersapp.presentation.features.search

import com.nab.doanngo.weathersapp.domain.models.dtos.WeatherForecast
import com.nab.doanngo.weathersapp.presentation.features.search.CityWeatherSearchDisplayModel.CITY_NAME_KEY_SEARCH_MIN_CHARACTER

data class CityWeatherSearchViewState(
    val loading: Boolean,
    val enteringCityName: String,
    val forecastDays: List<WeatherForecast>?
) {
    val forecastDaysDisplay: List<CityWeatherSearchDisplayModel.WeatherForecastDay>?
        get() = forecastDays?.map { dto ->
            CityWeatherSearchDisplayModel.WeatherForecastDay(
                date = dto.forecastDate,
                temperature = dto.temperatureAverage,
                temperatureUnit = dto.temperatureUnit,
                pressure = dto.pressure,
                humidity = dto.humidity,
                description = dto.weatherSummary?.description
            )
        }

    val ableToGetCityWeather: Boolean
        get() = enteringCityName.length >= CITY_NAME_KEY_SEARCH_MIN_CHARACTER
}
