package com.example.parkinggent.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://data.stad.gent/api/explore/v2.1/catalog/datasets/bezetting-parkeergarages-real-time/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        Json.asConverterFactory("application/json".toMediaType())
    )
    .baseUrl(BASE_URL)
    .build()

object ParkingGentApi{
    val retrofitService : ParkingApiService by lazy {
        retrofit.create(ParkingApiService::class.java)
    }
}

@Serializable
data class ApiResponse(val total_count: Int, val results: List<ApiParkingGent>)

interface ParkingApiService {
    @GET("records/")
    suspend fun getParkings(): ApiResponse
}