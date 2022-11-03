package com.nab.doanngo.weathersapp.domain.usecases

import com.nab.doanngo.weathersapp.domain.models.dtos.City
import com.nab.doanngo.weathersapp.domain.models.dtos.CityWeatherInformation
import com.nab.doanngo.weathersapp.domain.models.dtos.TemperatureUnit
import com.nab.doanngo.weathersapp.domain.models.dtos.WeatherForecast
import com.nab.doanngo.weathersapp.domain.models.exceptions.CityNotFoundException
import com.nab.doanngo.weathersapp.domain.repositories.CityWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.only
import org.mockito.kotlin.verify
import java.util.Date

// region Mock data
private const val cityNameMock = "Hanoi"

private val cityWeatherInfoMock = CityWeatherInformation(
    city = City(
        id = 123,
        country = "Vn",
        name = cityNameMock
    ),
    weatherNextDays = listOf(
        WeatherForecast(
            clouds = 1,
            deg = 1,
            forecastDate = Date(),
            temperatureAverage = 23.0,
            gust = 23.0,
            humidity = 50,
            pop = 40.0,
            pressure = 15,
            rain = 50.0,
            speed = 50.0,
            sunriseTime = Date(),
            sunsetTime = Date(),
            temperatureUnit = TemperatureUnit.Celsius
        )
    )
)

private val errorMock = CityNotFoundException(cityNameMock)
// endregion

/**
 * Class test for [GetCityWeatherForecastDaysUseCase]
 */
@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
class GetCityWeatherForecastDaysUseCaseTest {
    private val dispatcher = StandardTestDispatcher()

    private val scope = TestScope(dispatcher)

    private val cityWeatherRepositoryMock = mock<CityWeatherRepository>()

    private lateinit var getCityWeatherForecastDaysUseCase: GetCityWeatherForecastDaysUseCase

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        getCityWeatherForecastDaysUseCase = GetCityWeatherForecastDaysUseCase(
            dispatcher = dispatcher,
            cityWeatherRepository = cityWeatherRepositoryMock
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN repository return data successfully EXPECT use case return success with the data`() {
        scope.runTest {
            // MOCK
            given(cityWeatherRepositoryMock.getWeatherOfCity(cityNameMock))
                .willReturn(cityWeatherInfoMock)

            // EXECUTE
            val result = getCityWeatherForecastDaysUseCase(
                GetCityWeatherForecastDaysUseCase.Parameters(cityNameMock)
            )

            // VERIFY
            verify(cityWeatherRepositoryMock, only()).getWeatherOfCity(cityNameMock)
            assert(result.isSuccess)
            assert(result.getOrNull() == cityWeatherInfoMock)
        }
    }

    @Test
    fun `WHEN repository throw error EXPECT use case return fail with the error`() {
        scope.runTest {
            // MOCK
            given(cityWeatherRepositoryMock.getWeatherOfCity(cityNameMock))
                .willAnswer { throw errorMock }

            // EXECUTE
            val result = getCityWeatherForecastDaysUseCase(
                GetCityWeatherForecastDaysUseCase.Parameters(cityNameMock)
            )

            // VERIFY
            verify(cityWeatherRepositoryMock, only()).getWeatherOfCity(cityNameMock)
            assert(result.isFailure)
            assert(result.exceptionOrNull() is CityNotFoundException)
        }
    }
}
