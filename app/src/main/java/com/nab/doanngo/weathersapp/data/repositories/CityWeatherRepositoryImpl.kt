package com.nab.doanngo.weathersapp.data.repositories

import com.nab.doanngo.weathersapp.data.datasources.remote.WeatherRemoteDataSource
import com.nab.doanngo.weathersapp.data.datasources.remote.entities.ResponseCode
import com.nab.doanngo.weathersapp.data.datasources.remote.entities.toDto
import com.nab.doanngo.weathersapp.data.datasources.remote.entities.toDtos
import com.nab.doanngo.weathersapp.domain.models.dtos.CityWeatherInformation
import com.nab.doanngo.weathersapp.domain.models.exceptions.CityNotFoundException
import com.nab.doanngo.weathersapp.domain.models.exceptions.InternetTroubleException
import com.nab.doanngo.weathersapp.domain.models.exceptions.OthersRemoteErrorException
import com.nab.doanngo.weathersapp.domain.repositories.CityWeatherRepository
import com.nab.doanngo.weathersapp.domain.repositories.UserConfigurationRepository
import okio.IOException
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

class CityWeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val userConfigurationRepository: UserConfigurationRepository
) : CityWeatherRepository {
    override suspend fun getWeatherOfCity(city: String): CityWeatherInformation {
        // Get user config
        val temperatureUnit = userConfigurationRepository.getTemperatureUnit()
        val forecastDays = userConfigurationRepository.getNumberOfForecastDay()

        // Request API and handle exceptions
        val response = try {
            weatherRemoteDataSource.getCityWeatherForecast(
                city = city,
                forecastDays = forecastDays,
                temperatureUnit = temperatureUnit
            )
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                throw CityNotFoundException(city)
            } else {
                throw e
            }
        } catch (e: IOException) {
            throw InternetTroubleException(e.message)
        }

        // Convert response to domain model and handle error
        return when (response.cod) {
            ResponseCode.Success -> CityWeatherInformation(
                city = response.city?.toDto() ?: throw Exception("Response's city is null"),
                weatherNextDays = response.list?.toDtos(temperatureUnit) ?: emptyList()
            )
            ResponseCode.Others -> throw OthersRemoteErrorException
        }
    }
}
