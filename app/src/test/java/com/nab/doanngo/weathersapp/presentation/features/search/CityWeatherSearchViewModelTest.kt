package com.nab.doanngo.weathersapp.presentation.features.search

import com.nab.doanngo.weathersapp.InstantExecutorExtension
import com.nab.doanngo.weathersapp.domain.models.exceptions.CityNotFoundException
import com.nab.doanngo.weathersapp.domain.usecases.GetCityWeatherForecastDaysUseCase
import com.nab.doanngo.weathersapp.domain.usecases.cityNameMock
import com.nab.doanngo.weathersapp.domain.usecases.cityWeatherInfoMock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
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

/**
 * Class test for [CityWeatherSearchViewModel]
 */
@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class, InstantExecutorExtension::class)
class CityWeatherSearchViewModelTest {
    private lateinit var viewModel: CityWeatherSearchViewModel

    private val getCityWeatherForecastDaysUseCaseMock = mock<GetCityWeatherForecastDaysUseCase>()

    private val dispatcher = StandardTestDispatcher()

    private val scope = TestScope(dispatcher)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        viewModel = CityWeatherSearchViewModel(getCityWeatherForecastDaysUseCaseMock)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN onEnteringCityName is called with value EXPECT the enteringCityName state is updated correctly`() {
        // EXECUTE
        viewModel.onEnteringCityName(cityNameMock)

        // VERIFY
        assert(viewModel.currentState.enteringCityName == cityNameMock)
    }

    @Test
    fun `WHEN getCityWeatherForecast is called and use case returned successfully EXPECT the enteringCityName state is updated correctly`() {
        scope.runTest {
            // MOCK
            given(
                getCityWeatherForecastDaysUseCaseMock(
                    GetCityWeatherForecastDaysUseCase.Parameters(
                        cityNameMock
                    )
                )
            ).willReturn(Result.success(cityWeatherInfoMock))

            // EXECUTE
            viewModel.getCityWeatherForecast(cityNameMock)
            advanceTimeBy(1000)

            // VERIFY
            assert(viewModel.currentState.forecastDays == cityWeatherInfoMock.weatherNextDays)
            assert(!viewModel.currentState.loading)
        }
    }

    @Test
    fun `WHEN getCityWeatherForecast is called and use case returned failure EXPECT the enteringCityName state is updated correctly`() {
        scope.runTest {
            // MOCK
            given(
                getCityWeatherForecastDaysUseCaseMock(
                    GetCityWeatherForecastDaysUseCase.Parameters(
                        cityNameMock
                    )
                )
            ).willReturn(Result.failure(CityNotFoundException(cityNameMock)))

            // EXECUTE
            viewModel.getCityWeatherForecast(cityNameMock)
            advanceTimeBy(1000)

            // VERIFY
            assert(viewModel.currentState.forecastDays!!.isEmpty())
            assert(!viewModel.currentState.loading)
        }
    }
}
