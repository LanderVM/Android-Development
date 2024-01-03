package com.example.parkinggent.data

import android.content.Context
import com.example.parkinggent.data.database.ParkingDb
import com.example.parkinggent.network.ParkingApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val parkingRepository: ParkingRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    private val baseUrl =
        "https://data.stad.gent/api/explore/v2.1/catalog/datasets/bezetting-parkeergarages-real-time/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ParkingApiService by lazy {
        retrofit.create(ParkingApiService::class.java)
    }


    override val parkingRepository: ParkingRepository by lazy {
        CachingParkingsRepository(
            ParkingDb.getDatabase(context = context).parkingDao(),
            retrofitService
        )
    }

}