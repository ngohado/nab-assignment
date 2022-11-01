package com.nab.doanngo.weathersapp.data.datasources.remote.entities

import com.nab.doanngo.weathersapp.domain.models.dtos.TemperatureUnit
import com.nab.doanngo.weathersapp.domain.models.dtos.WeatherForecast
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastEntity(
    val clouds: Int,
    val deg: Int,
    val dt: Int,
    @SerialName("feels_like")
    val feelsLike: TemperatureFeelEntity,
    val gust: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val speed: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: TemperatureForecastEntity,
    val weather: List<WeatherSummaryEntity>
)

// region Converter
fun WeatherForecastEntity.toDto(temperatureUnit: TemperatureUnit): WeatherForecast =
    WeatherForecast(
        clouds = clouds,
        deg = deg,
        forecastDate = dt.secondTimestampToDate(),
        temperatureAverage = temp.day,
        gust = gust,
        humidity = humidity,
        pop = pop,
        pressure = pressure,
        rain = rain,
        speed = speed,
        sunriseTime = sunrise.secondTimestampToDate(),
        sunsetTime = sunset.secondTimestampToDate(),
        temperatureUnit = temperatureUnit
    )

fun List<WeatherForecastEntity>.toDtos(
    temperatureUnit: TemperatureUnit
): List<WeatherForecast> = map { entity ->
    entity.toDto(temperatureUnit)
}
// endregion
