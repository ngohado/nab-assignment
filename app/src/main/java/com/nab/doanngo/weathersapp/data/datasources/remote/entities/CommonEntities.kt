package com.nab.doanngo.weathersapp.data.datasources.remote.entities

import com.nab.doanngo.weathersapp.data.utils.ResponseCodeSerializer
import com.nab.doanngo.weathersapp.domain.models.dtos.TemperatureUnit
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable(with = ResponseCodeSerializer::class)
enum class ResponseCode(val code: Int) {
    Success(code = 200),
    Others(code = -1)
}

// region Converters
/**
 * Convert [TemperatureUnit] values to string values which are accepted by Server.
 */
fun TemperatureUnit.toAPIRequestValue(): String? {
    return when (this) {
        TemperatureUnit.Celsius -> "metric"
        TemperatureUnit.Fahrenheit -> "imperial"
        TemperatureUnit.Kelvin -> null // Default don't send
    }
}

/**
 * Convert timestamp in second to [Date].
 */
fun Int.secondTimestampToDate(): Date = Date(this * 1000L)
// endregion
