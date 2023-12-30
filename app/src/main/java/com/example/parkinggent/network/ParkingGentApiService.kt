package com.example.parkinggent.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET

interface ParkingApiService {
    @GET("records?limit=20")
    suspend fun getParkings():ApiParkingResponse
}

fun ParkingApiService.getParkingsAsFlow(): Flow<List<ApiParkingGent>> = flow {
    try {
        val response = getParkings()
        emit(response.results)
    }
    catch(e: Exception){
        Log.e("API", "getParkingsAsFlow: "+e.stackTraceToString(), )
    }
}