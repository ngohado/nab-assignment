package com.nab.doanngo.weathersapp.presentation.features.search

import com.nab.doanngo.weathersapp.presentation.features.search.CityWeatherSearchDisplayModel.CITY_NAME_KEY_SEARCH_MIN_CHARACTER
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

/**
 * Class test for [CityWeatherSearchViewState]
 */
@ExtendWith(MockitoExtension::class)
class CityWeatherSearchViewStateTest {
    private lateinit var viewState: CityWeatherSearchViewState

    @BeforeEach
    fun setUp() {
        viewState = CityWeatherSearchViewState(
            loading = false,
            enteringCityName = "",
            forecastDays = null
        )
    }

    @Test
    fun `WHEN enteringCityName is received string with length smaller than CITY_NAME_KEY_SEARCH_MIN_CHARACTER EXPECT ableToGetCityWeather is false`() {
        // MOCK
        val cityNameMock = (1 until CITY_NAME_KEY_SEARCH_MIN_CHARACTER).joinToString(separator = "")

        // EXECUTE
        viewState = viewState.copy(enteringCityName = cityNameMock)

        // VERIFY
        assert(!viewState.ableToGetCityWeather)
    }

    @Test
    fun `WHEN enteringCityName is received string with length equal CITY_NAME_KEY_SEARCH_MIN_CHARACTER EXPECT ableToGetCityWeather is true`() {
        // MOCK
        val cityNameMock = (1..CITY_NAME_KEY_SEARCH_MIN_CHARACTER).joinToString(separator = "")

        // EXECUTE
        viewState = viewState.copy(enteringCityName = cityNameMock)

        // VERIFY
        assert(viewState.ableToGetCityWeather)
    }

    @Test
    fun `WHEN enteringCityName is received string with length larger than CITY_NAME_KEY_SEARCH_MIN_CHARACTER EXPECT ableToGetCityWeather is true`() {
        // MOCK
        val cityNameMock = (1..CITY_NAME_KEY_SEARCH_MIN_CHARACTER + 1).joinToString(separator = "")

        // EXECUTE
        viewState = viewState.copy(enteringCityName = cityNameMock)

        // VERIFY
        assert(viewState.ableToGetCityWeather)
    }
}
