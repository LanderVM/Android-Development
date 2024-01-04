package com.example.parkinggent.network

import com.example.parkinggent.model.Location
import com.example.parkinggent.model.ParkingInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ApiParkingResponse(
    @SerialName("total_count")
    val totalCount: Int,
    val results: List<ApiParkingGent>
)

@Serializable
data class ApiParkingGent(
    val name: String,
    val lastupdate: String,
    val totalcapacity: Int,
    val availablecapacity: Int,
    val occupation: Int,
    val type: String,
    val description: String,
    val id: String,
    val openingtimesdescription: String,
    val isopennow: Int,
    val temporaryclosed: Int,
    val operatorinformation: String,
    val freeparking: Int,
    val urllinkaddress: String,
    val occupancytrend: String,
    val locationanddimension: String,
    val location: Location,
    val text: String?,
    val categorie: String
)

/**
 * Extension function to map a Flow of ApiParkingGent objects to a Flow of ParkingInfo objects.
 * @return A Flow emitting lists of ParkingInfo objects.
 */
fun Flow<List<ApiParkingGent>>.asDomainObjects(): Flow<List<ParkingInfo>> {
    return map {
        it.asDomainObjects()
    }
}

/**
 * Extension function to convert a list of ApiParkingGent objects to a list of ParkingInfo objects.
 * @return A list of ParkingInfo objects.
 */
fun List<ApiParkingGent>.asDomainObjects(): List<ParkingInfo> {
    val domainList = this.map {
        ParkingInfo(
            it.name,
            it.lastupdate,
            it.totalcapacity,
            it.availablecapacity,
            it.occupation,
            it.type,
            it.description,
            it.id,
            it.openingtimesdescription,
            intToBoolean(it.isopennow),
            intToBoolean(it.temporaryclosed),
            it.operatorinformation,
            intToBoolean(it.freeparking),
            it.urllinkaddress,
            it.occupancytrend,
            Json.decodeFromString(it.locationanddimension),
            it.location,
            it.text,
            it.categorie
        )
    }
    return domainList
}

/**
 * Converts an integer to a boolean value.
 * @param value The integer value to convert.
 * @return True if value is non-zero, false otherwise.
 */
fun intToBoolean(value: Int): Boolean {
    return value != 0
}