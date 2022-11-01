package com.nab.doanngo.weathersapp.data.datasources.remote

import com.nab.doanngo.weathersapp.data.datasources.remote.entities.CityWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

// region API sub-paths
private const val CITY_FORECAST_DAYS = "forecast/daily?appid=60c6fbeb4b93ac653c492ba806fc346d"
// endregion

interface WeatherAPIService {
    @GET(CITY_FORECAST_DAYS)
    suspend fun getCityWeatherForecast(
        @Query("q") city: String,
        @Query("cnt") forecastDays: Int,
        @Query("units") temperatureUnit: String?
    ): CityWeatherResponse
}
