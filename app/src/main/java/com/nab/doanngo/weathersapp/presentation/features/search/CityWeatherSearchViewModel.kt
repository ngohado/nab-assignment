package com.nab.doanngo.weathersapp.presentation.features.search

import androidx.lifecycle.viewModelScope
import com.nab.doanngo.weathersapp.domain.usecases.GetCityWeatherForecastDaysUseCase
import com.nab.doanngo.weathersapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityWeatherSearchViewModel @Inject constructor(
    private val getCityWeatherForecastDaysUseCase: GetCityWeatherForecastDaysUseCase
) : BaseViewModel<CityWeatherSearchViewState, CityWeatherSearchEvent>() {
    override fun initState(): CityWeatherSearchViewState {
        return CityWeatherSearchViewState(
            loading = false,
            enteringCityName = "",
            forecastDays = null
        )
    }

    fun onEnteringCityName(text: String) {
        dispatchState(currentState.copy(enteringCityName = text))
    }

    fun getCityWeatherForecast(city: String) {
        viewModelScope.launch {
            dispatchState(currentState.copy(loading = true))

            getCityWeatherForecastDaysUseCase(
                GetCityWeatherForecastDaysUseCase.Parameters(city)
            ).onSuccess { cityWeatherInformation ->
                dispatchState(
                    currentState.copy(
                        forecastDays = cityWeatherInformation.weatherNextDays,
                        loading = false
                    )
                )
            }.onFailure { error ->
                dispatchEvent(CityWeatherSearchEvent.Error(error))

                dispatchState(currentState.copy(forecastDays = emptyList(), loading = false))
            }
        }
    }
}
