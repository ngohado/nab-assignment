package com.nab.doanngo.weathersapp.presentation.features.search

sealed interface CityWeatherSearchEvent {
    data class Error(val error: Throwable) : CityWeatherSearchEvent

    data class OpenCityWeatherDetailEvent(
        val id: Int
    ) : CityWeatherSearchEvent
}
