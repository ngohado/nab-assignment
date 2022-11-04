package com.nab.doanngo.weathersapp.data.datasources.remote.entities

import kotlinx.serialization.Serializable

@Serializable
data class TemperatureFeelEntity(
    val day: Double?,
    val eve: Double?,
    val morn: Double?,
    val night: Double?
)
