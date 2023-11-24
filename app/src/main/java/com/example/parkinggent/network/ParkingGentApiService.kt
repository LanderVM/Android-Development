package com.example.parkinggent.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://data.stad.gent/api/explore/v2.1/catalog/datasets/bezetting-parkeergarages-real-time/records"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        Json.asConverterFactory("application/json".toMediaType())
    )
    .baseUrl(BASE_URL)
    .build()

object ParkingGentApi{
    val retrofitService : TaskApiService by lazy {
        retrofit.create(TaskApiService::class.java)
    }
}

interface TaskApiService {
    @GET("parkings")
    suspend fun getParkings(): List<ApiParkingGent>
}