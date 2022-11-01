package com.nab.doanngo.weathersapp.data.datasources.remote.entities

import kotlinx.serialization.Serializable

@Serializable
data class WeatherSummaryEntity(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)
