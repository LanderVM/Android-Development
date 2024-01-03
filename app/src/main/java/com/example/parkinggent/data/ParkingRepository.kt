package com.example.parkinggent.data

import android.util.Log
import com.example.parkinggent.data.database.ParkingDao
import com.example.parkinggent.data.database.asDbParking
import com.example.parkinggent.data.database.asDomainParking
import com.example.parkinggent.data.database.asDomainParkings
import com.example.parkinggent.model.ParkingInfo
import com.example.parkinggent.network.ParkingApiService
import com.example.parkinggent.network.asDomainObjects
import com.example.parkinggent.network.getParkingsAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException

interface ParkingRepository {
    fun getParking(): Flow<List<ParkingInfo>>
    fun getParking(id: String): Flow<ParkingInfo?>
    suspend fun insertParking(parking: ParkingInfo)
    suspend fun refresh()
}

class CachingParkingsRepository(
    private val parkingDao: ParkingDao,
    private val parkingApiService: ParkingApiService
) : ParkingRepository {
    override fun getParking(): Flow<List<ParkingInfo>> {
        return parkingDao.getAllItems().map {
            it.asDomainParkings()
        }.onEach {
            if (it.isEmpty()) {
                refresh()
            }
        }
    }

    override fun getParking(id: String): Flow<ParkingInfo?> {
        return parkingDao.getItem(id).map {
            it.asDomainParking()
        }
    }

    override suspend fun insertParking(parking: ParkingInfo) {
        parkingDao.insert(parking.asDbParking())
    }

    override suspend fun refresh() {
        try {
            parkingApiService.getParkingsAsFlow().asDomainObjects().collect { value ->
                for (parking in value) {
                    insertParking(parking)
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.i("Repository refresh", "${e.message}")
        }

    }
}