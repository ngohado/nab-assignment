package com.nab.doanngo.weathersapp.data.datasources.remote.entities

import kotlinx.serialization.Serializable

@Serializable
data class CoordinatorEntity(
    val lat: Double,
    val lon: Double
)
