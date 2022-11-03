package com.nab.doanngo.weathersapp.data.repositories

import com.nab.doanngo.weathersapp.data.datasources.remote.WeatherRemoteDataSource
import com.nab.doanngo.weathersapp.data.datasources.remote.entities.CityEntity
import com.nab.doanngo.weathersapp.data.datasources.remote.entities.CityWeatherResponse
import com.nab.doanngo.weathersapp.data.datasources.remote.entities.CoordinatorEntity
import com.nab.doanngo.weathersapp.data.datasources.remote.entities.ResponseCode
import com.nab.doanngo.weathersapp.data.datasources.remote.entities.TemperatureFeelEntity
import com.nab.doanngo.weathersapp.data.datasources.remote.entities.TemperatureForecastEntity
import com.nab.doanngo.weathersapp.data.datasources.remote.entities.WeatherForecastEntity
import com.nab.doanngo.weathersapp.data.datasources.remote.entities.toDto
import com.nab.doanngo.weathersapp.data.datasources.remote.entities.toDtos
import com.nab.doanngo.weathersapp.domain.models.dtos.CityWeatherInformation
import com.nab.doanngo.weathersapp.domain.models.dtos.TemperatureUnit
import com.nab.doanngo.weathersapp.domain.models.exceptions.CityNotFoundException
import com.nab.doanngo.weathersapp.domain.models.exceptions.InternetTroubleException
import com.nab.doanngo.weathersapp.domain.models.exceptions.OthersRemoteErrorException
import com.nab.doanngo.weathersapp.domain.repositories.UserConfigurationRepository
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
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

// region Mock
private val temperatureUnitMock = TemperatureUnit.Celsius

private const val nextDaysForecastMock = 7

private const val cityNameMock = "Hanoi"

private val responseMock = CityWeatherResponse(
    city = CityEntity(
        coord = CoordinatorEntity(
            lat = 10.8333,
            lon = 106.6667
        ),
        country = "VN",
        id = 1580578,
        name = "Ho Chi Minh City",
        population = 0,
        timezone = 25200
    ),
    cnt = nextDaysForecastMock,
    cod = ResponseCode.Success,
    list = listOf(
        WeatherForecastEntity(
            clouds = 40,
            deg = 167,
            dt = 1667275200,
            feelsLike = TemperatureFeelEntity(
                day = 29.97,
                eve = 29.97,
                morn = 29.97,
                night = 29.97
            ),
            gust = 4.49,
            humidity = 70,
            pop = 0.43,
            pressure = 43,
            rain = 0.27,
            speed = 2.51,
            sunrise = 1667256274,
            sunset = 1667298549,
            temp = TemperatureForecastEntity(
                day = 29.97,
                eve = 23.33,
                max = 29.97,
                min = 27.23,
                morn = 23.33,
                night = 23.95
            ),
            weather = listOf()
        )
    ),
    message = 123.0
)
// endregion

/**
 * Class test for [CityWeatherRepositoryImpl]
 */
@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
class CityWeatherRepositoryImplTest {
    private val dispatcher = StandardTestDispatcher()

    private val scope = TestScope(dispatcher)

    private val weatherRemoteDataSourceMock = mock<WeatherRemoteDataSource>()
    private val userConfigurationRepositoryMock = mock<UserConfigurationRepository>()

    private lateinit var cityWeatherRepository: CityWeatherRepositoryImpl

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        cityWeatherRepository = CityWeatherRepositoryImpl(
            weatherRemoteDataSource = weatherRemoteDataSourceMock,
            userConfigurationRepository = userConfigurationRepositoryMock
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN remote data source get data successfully EXPECT return correct data`() {
        scope.runTest {
            // MOCK
            given(userConfigurationRepositoryMock.getTemperatureUnit())
                .willReturn(temperatureUnitMock)

            given(userConfigurationRepositoryMock.getNumberOfForecastDay())
                .willReturn(nextDaysForecastMock)

            given(
                weatherRemoteDataSourceMock.getCityWeatherForecast(
                    city = cityNameMock,
                    forecastDays = nextDaysForecastMock,
                    temperatureUnit = temperatureUnitMock
                )
            ).willReturn(responseMock)

            // EXECUTE
            val cityWeatherInfo = cityWeatherRepository.getWeatherOfCity(cityNameMock)

            // VERIFY
            assert(
                cityWeatherInfo == CityWeatherInformation(
                    city = responseMock.city!!.toDto(),
                    weatherNextDays = responseMock.list?.toDtos(temperatureUnitMock) ?: emptyList()
                )
            )
        }
    }

    @Test
    fun `WHEN remote data source return 404 EXPECT throw CityNotFoundException`() {
        scope.runTest {
            // MOCK
            given(userConfigurationRepositoryMock.getTemperatureUnit())
                .willReturn(temperatureUnitMock)

            given(userConfigurationRepositoryMock.getNumberOfForecastDay())
                .willReturn(nextDaysForecastMock)

            given(
                weatherRemoteDataSourceMock.getCityWeatherForecast(
                    city = cityNameMock,
                    forecastDays = nextDaysForecastMock,
                    temperatureUnit = temperatureUnitMock
                )
            ).willAnswer {
                val notFoundExceptionMock = mock<HttpException>()
                given(notFoundExceptionMock.code()).willReturn(HttpURLConnection.HTTP_NOT_FOUND)

                throw notFoundExceptionMock
            }

            // EXECUTE && VERIFY
            assertThrows<CityNotFoundException> {
                cityWeatherRepository.getWeatherOfCity(cityNameMock)
            }
        }
    }

    @Test
    fun `WHEN remote data source return HttpException but other code EXPECT throw same exception to above`() {
        scope.runTest {
            // MOCK
            given(userConfigurationRepositoryMock.getTemperatureUnit())
                .willReturn(temperatureUnitMock)

            given(userConfigurationRepositoryMock.getNumberOfForecastDay())
                .willReturn(nextDaysForecastMock)

            val forbiddenExceptionMock = mock<HttpException>()
            given(forbiddenExceptionMock.code()).willReturn(HttpURLConnection.HTTP_FORBIDDEN)

            given(
                weatherRemoteDataSourceMock.getCityWeatherForecast(
                    city = cityNameMock,
                    forecastDays = nextDaysForecastMock,
                    temperatureUnit = temperatureUnitMock
                )
            ).willAnswer {
                throw forbiddenExceptionMock
            }

            // EXECUTE && VERIFY
            val exception = assertThrows<HttpException> {
                cityWeatherRepository.getWeatherOfCity(cityNameMock)
            }
            assert(exception.code() == forbiddenExceptionMock.code())
        }
    }

    @Test
    fun `WHEN remote data source return IOException EXPECT throw InternetTroubleException`() {
        scope.runTest {
            // MOCK
            given(userConfigurationRepositoryMock.getTemperatureUnit())
                .willReturn(temperatureUnitMock)

            given(userConfigurationRepositoryMock.getNumberOfForecastDay())
                .willReturn(nextDaysForecastMock)

            given(
                weatherRemoteDataSourceMock.getCityWeatherForecast(
                    city = cityNameMock,
                    forecastDays = nextDaysForecastMock,
                    temperatureUnit = temperatureUnitMock
                )
            ).willAnswer {
                throw IOException()
            }

            // EXECUTE && VERIFY
            assertThrows<InternetTroubleException> {
                cityWeatherRepository.getWeatherOfCity(cityNameMock)
            }
        }
    }

    @Test
    fun `WHEN remote data source get data successfully, but the code is not equal 200 EXPECT throw OthersRemoteErrorException`() {
        scope.runTest {
            // MOCK
            given(userConfigurationRepositoryMock.getTemperatureUnit())
                .willReturn(temperatureUnitMock)

            given(userConfigurationRepositoryMock.getNumberOfForecastDay())
                .willReturn(nextDaysForecastMock)

            given(
                weatherRemoteDataSourceMock.getCityWeatherForecast(
                    city = cityNameMock,
                    forecastDays = nextDaysForecastMock,
                    temperatureUnit = temperatureUnitMock
                )
            ).willReturn(responseMock.copy(cod = ResponseCode.Others))

            // EXECUTE && VERIFY
            assertThrows<OthersRemoteErrorException> {
                cityWeatherRepository.getWeatherOfCity(cityNameMock)
            }
        }
    }
}
