package com.nab.doanngo.weathersapp.presentation.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.nab.doanngo.weathersapp.R
import com.nab.doanngo.weathersapp.databinding.FragmentCityWeatherSearchBinding
import com.nab.doanngo.weathersapp.presentation.base.BaseFragment
import com.nab.doanngo.weathersapp.presentation.features.search.adapter.WeatherForecastAdapter
import com.nab.doanngo.weathersapp.presentation.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityWeatherSearchFragment : BaseFragment<FragmentCityWeatherSearchBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCityWeatherSearchBinding
        get() = FragmentCityWeatherSearchBinding::inflate

    private val viewModel: CityWeatherSearchViewModel by viewModels()

    private val adapter: WeatherForecastAdapter?
        get() = binding.rvWeatherForecastDays.adapter as? WeatherForecastAdapter

    override fun initData(data: Bundle?) {
        // Do nothing
    }

    override fun initViews() {
        binding.rvWeatherForecastDays.adapter = WeatherForecastAdapter()
    }

    override fun initActions() {
        binding.edtCitySearch.apply {
            doOnTextChanged { text, _, _, _ ->
                viewModel.onEnteringCityName(text?.trim()?.toString() ?: "")
            }

            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) hideKeyboard()
            }
        }

        binding.btnSearchWeather.setOnClickListener {
            binding.edtCitySearch.clearFocus()
            viewModel.getCityWeatherForecast(city = viewModel.currentState.enteringCityName)
        }
    }

    override fun initObservers() {
        // region Observe events
        viewModel.observeEvent(
            viewLifecycleOwner = viewLifecycleOwner
        ) { event ->
            when (event) {
                is CityWeatherSearchEvent.Error -> {
                    val error = event.error
                    // Handle error here later
                }
                is CityWeatherSearchEvent.OpenCityWeatherDetailEvent -> {
                    val id = event.id
                    // Open detail screen
                }
            }
        }
        // endregion

        // region Observe states
        viewModel.observe(
            owner = viewLifecycleOwner,
            selector = { state -> state.loading }
        ) { loading ->
            binding.loadingView.isVisible = loading
        }

        viewModel.observe(
            owner = viewLifecycleOwner,
            selector = { state -> state.ableToGetCityWeather }
        ) { ableToGetCityWeather ->
            binding.btnSearchWeather.isEnabled = ableToGetCityWeather
        }

        viewModel.observe(
            owner = viewLifecycleOwner,
            selector = { state -> state.forecastDaysDisplay }
        ) { forecastDaysDisplay ->
            // If forecastDaysDisplay is null, it isn't a result of entered city name (in case
            // is the first times come to screen)
            binding.tvEmptyAlert.apply {
                isVisible = forecastDaysDisplay != null && forecastDaysDisplay.isEmpty()
                if (isVisible) {
                    text = getString(
                        R.string.city_weather_search_empty_text,
                        viewModel.currentState.enteringCityName
                    )
                }
            }

            adapter?.submitList(forecastDaysDisplay)
        }
        // endregion
    }
}
