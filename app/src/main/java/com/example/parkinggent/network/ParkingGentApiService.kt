package com.example.parkinggent.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET

/**
 * A Retrofit service interface for fetching parking data from an API.
 */
interface ParkingApiService {
    /**
     * Fetches a list of parking records from the API.
     * @return A response object containing parking data.
     */
    @GET("records?limit=20")
    suspend fun getParkings(): ApiParkingResponse
}

/**
 * Extension function for the ParkingApiService to fetch parking data as a Flow.
 * @return A Flow emitting lists of ApiParkingGent objects.
 */
fun ParkingApiService.getParkingsAsFlow(): Flow<List<ApiParkingGent>> = flow {
    try {
        val response = getParkings()
        emit(response.results)
    } catch (e: Exception) {
        Log.e("API", "getParkingsAsFlow: " + e.stackTraceToString())
    }
}