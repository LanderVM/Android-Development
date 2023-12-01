package com.example.parkinggent.data

import com.example.parkinggent.model.ParkingInfo
import com.example.parkinggent.network.ParkingApiService
import com.example.parkinggent.network.asDomainObjects

interface ParkingRepository {
    suspend fun getParking(): List<ParkingInfo>
}
class ApiParkingRepository(
    private val parkingApiService: ParkingApiService
): ParkingRepository{
    override suspend fun getParking(): List<ParkingInfo> {
        return parkingApiService.getParkings().results.asDomainObjects()
    }
}