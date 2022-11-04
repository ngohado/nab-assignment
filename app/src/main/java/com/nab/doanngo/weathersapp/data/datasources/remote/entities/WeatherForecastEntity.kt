package com.nab.doanngo.weathersapp.data.datasources.remote.entities

import com.nab.doanngo.weathersapp.domain.models.dtos.TemperatureUnit
import com.nab.doanngo.weathersapp.domain.models.dtos.WeatherForecast
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class WeatherForecastEntity(
    val clouds: Int?,
    val deg: Int?,
    val dt: Int?,
    @SerialName("feels_like")
    val feelsLike: TemperatureFeelEntity?,
    val gust: Double?,
    val humidity: Int?,
    val pop: Double?,
    val pressure: Int?,
    val rain: Double?,
    val speed: Double?,
    val sunrise: Int?,
    val sunset: Int?,
    val temp: TemperatureForecastEntity?,
    val weather: List<WeatherSummaryEntity>?
)

// region Converter
fun WeatherForecastEntity.toDto(temperatureUnit: TemperatureUnit): WeatherForecast =
    WeatherForecast(
        clouds = clouds ?: 0,
        deg = deg ?: 0,
        forecastDate = dt?.secondTimestampToDate() ?: Date(),
        temperatureAverage = temp?.day ?: 0.0,
        gust = gust ?: 0.0,
        humidity = humidity ?: 0,
        pop = pop ?: 0.0,
        pressure = pressure ?: 0,
        rain = rain ?: 0.0,
        speed = speed ?: 0.0,
        sunriseTime = sunrise?.secondTimestampToDate() ?: Date(),
        sunsetTime = sunset?.secondTimestampToDate() ?: Date(),
        weatherSummary = weather?.firstOrNull()?.toDto(),
        temperatureUnit = temperatureUnit
    )

fun List<WeatherForecastEntity>.toDtos(
    temperatureUnit: TemperatureUnit
): List<WeatherForecast> = map { entity ->
    entity.toDto(temperatureUnit)
}
// endregion
