package com.cloniamix.lesson_9_engurazov_kotlin.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object WeatherApiClient {
    private const val BASE_URL = "https://api.openweathermap.org"

    val getClient: WeatherApi
        get() {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(WeatherApi::class.java)
        }


}