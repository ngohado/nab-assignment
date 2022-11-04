package com.nab.doanngo.weathersapp.domain.models.dtos

enum class TemperatureUnit {
    Celsius,
    Fahrenheit,
    Kelvin
}

fun TemperatureUnit.format(temperature: Double): String {
    return when (this) {
        TemperatureUnit.Celsius -> "%.2f°C"
        TemperatureUnit.Fahrenheit -> "%.2f°F"
        TemperatureUnit.Kelvin -> "%.2f°K"
    }.format(temperature)
}
