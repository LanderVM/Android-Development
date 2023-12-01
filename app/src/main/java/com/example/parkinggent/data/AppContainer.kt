package com.example.parkinggent.data

import com.example.parkinggent.network.ParkingApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val parkingRepository: ParkingRepository
}

//container that takes care of dependencies
class DefaultAppContainer(): AppContainer{

    private val baseUrl = "https://data.stad.gent/api/explore/v2.1/catalog/datasets/bezetting-parkeergarages-real-time/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : ParkingApiService by lazy {
        retrofit.create(ParkingApiService::class.java)
    }


    override val parkingRepository: ParkingRepository by lazy {
        ApiParkingRepository(retrofitService)
    }

}