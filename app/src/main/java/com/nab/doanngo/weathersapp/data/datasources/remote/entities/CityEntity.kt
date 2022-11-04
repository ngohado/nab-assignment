package com.nab.doanngo.weathersapp.data.datasources.remote.entities

import com.nab.doanngo.weathersapp.domain.models.dtos.City
import kotlinx.serialization.Serializable

@Serializable
data class CityEntity(
    val coord: CoordinatorEntity?,
    val country: String?,
    val id: Int?,
    val name: String?,
    val population: Int?,
    val timezone: Int?
)

// region Converters
fun CityEntity.toDto(): City = City(
    id = id ?: 0,
    name = name ?: "",
    country = country ?: ""
)
// endregion
