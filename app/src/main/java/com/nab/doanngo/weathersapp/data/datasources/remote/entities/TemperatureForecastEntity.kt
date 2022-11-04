package com.nab.doanngo.weathersapp.data.datasources.remote.entities

import kotlinx.serialization.Serializable

@Serializable
data class TemperatureForecastEntity(
    val day: Double?,
    val eve: Double?,
    val max: Double?,
    val min: Double?,
    val morn: Double?,
    val night: Double?
)
