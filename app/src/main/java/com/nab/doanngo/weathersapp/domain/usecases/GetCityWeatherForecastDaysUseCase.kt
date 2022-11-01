package com.nab.doanngo.weathersapp.domain.usecases

import com.nab.doanngo.weathersapp.di.IoDispatcher
import com.nab.doanngo.weathersapp.domain.UseCase
import com.nab.doanngo.weathersapp.domain.models.dtos.CityWeatherInformation
import com.nab.doanngo.weathersapp.domain.repositories.CityWeatherRepository
import com.nab.doanngo.weathersapp.domain.repositories.UserConfigurationRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Request get weather forecast of city name in next days
 * (defined by user config @see [UserConfigurationRepository.getNumberOfForecastDay])
 */
class GetCityWeatherForecastDaysUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val cityWeatherRepository: CityWeatherRepository
) : UseCase<GetCityWeatherForecastDaysUseCase.Parameters, CityWeatherInformation>(dispatcher) {

    /**
     * Input for [GetCityWeatherForecastDaysUseCase]
     * @property city: City name.
     */
    data class Parameters(
        val city: String
    )

    override suspend fun execute(parameters: Parameters): CityWeatherInformation {
        return cityWeatherRepository.getWeatherOfCity(parameters.city)
    }
}
