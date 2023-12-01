package com.example.parkinggent.network

import kotlinx.serialization.Serializable
import retrofit2.http.GET

@Serializable
data class ApiResponse(val total_count: Int, val results: List<ApiParkingGent>)

interface ParkingApiService {
    @GET("records/")
    suspend fun getParkings():ApiResponse
}