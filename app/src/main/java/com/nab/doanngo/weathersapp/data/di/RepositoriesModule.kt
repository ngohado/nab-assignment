package com.nab.doanngo.weathersapp.data.di

import com.nab.doanngo.weathersapp.data.repositories.CityWeatherRepositoryImpl
import com.nab.doanngo.weathersapp.data.repositories.UserConfigurationRepositoryImpl
import com.nab.doanngo.weathersapp.domain.repositories.CityWeatherRepository
import com.nab.doanngo.weathersapp.domain.repositories.UserConfigurationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    abstract fun provideCityWeatherRepository(
        impl: CityWeatherRepositoryImpl
    ): CityWeatherRepository

    @Binds
    abstract fun provideUserConfigurationRepository(
        impl: UserConfigurationRepositoryImpl
    ): UserConfigurationRepository
}
