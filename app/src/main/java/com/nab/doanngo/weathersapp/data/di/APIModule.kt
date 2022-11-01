package com.nab.doanngo.weathersapp.data.di

import androidx.viewbinding.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nab.doanngo.weathersapp.data.datasources.remote.WeatherAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@OptIn(ExperimentalSerializationApi::class)
@Module
@InstallIn(SingletonComponent::class)
class APIModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(CALL_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    val loggingInterceptor = HttpLoggingInterceptor()
                        .apply { level = HttpLoggingInterceptor.Level.BODY }
                    addInterceptor(loggingInterceptor)
                }
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherAPIService(okHttpClient: OkHttpClient): WeatherAPIService {
        val responseContentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory(responseContentType))
            .baseUrl(WEATHER_MAIN_URL)
            .client(okHttpClient)
            .build()
            .create(WeatherAPIService::class.java)
    }

    companion object {
        const val CALL_TIMEOUT: Long = 40_000

        const val CONNECT_TIMEOUT: Long = 40_000

        const val READ_TIMEOUT: Long = 40_000

        const val WRITE_TIMEOUT: Long = 40_000

        const val WEATHER_MAIN_URL = "https://api.openweathermap.org/data/2.5/"
    }
}
