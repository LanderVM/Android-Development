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

/**
 * Defines the interface for a repository managing parking data.
 */
interface ParkingRepository {
    /**
     * Retrieves a flow of a list of all parking spots.
     *
     * @return A [Flow] emitting a list of [ParkingInfo] objects.
     */
    fun getParking(): Flow<List<ParkingInfo>>

    /**
     * Retrieves a flow of a specific parking spot by its ID.
     *
     * @param id The unique identifier of the parking spot.
     * @return A [Flow] emitting the requested [ParkingInfo] object.
     */
    fun getParking(id: String): Flow<ParkingInfo?>

    /**
     * Inserts a parking spot into the repository.
     *
     * @param parking The [ParkingInfo] object to insert.
     */
    suspend fun insertParking(parking: ParkingInfo)

    /**
     * Refreshes the parking data from the remote source.
     */
    suspend fun refresh()
}

/**
 * An implementation of [ParkingRepository] that caches parking data and fetches from a remote API.
 *
 * @property parkingDao The DAO for accessing the local database.
 * @property parkingApiService The service for accessing the remote API.
 */
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