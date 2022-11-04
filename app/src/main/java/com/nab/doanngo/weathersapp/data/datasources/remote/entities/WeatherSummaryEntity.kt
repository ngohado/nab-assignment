package com.nab.doanngo.weathersapp.data.datasources.remote.entities

import com.nab.doanngo.weathersapp.domain.models.dtos.WeatherSummary
import kotlinx.serialization.Serializable

@Serializable
data class WeatherSummaryEntity(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

// region Converters
fun WeatherSummaryEntity.toDto(): WeatherSummary = WeatherSummary(
    id = id,
    description = description,
    main = main
)
// endregion
