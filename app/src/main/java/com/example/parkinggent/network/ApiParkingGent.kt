package com.example.parkinggent.network

import com.example.parkinggent.model.Location
import com.example.parkinggent.model.LocationAndDimension
import com.example.parkinggent.model.ParkingInfo
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ApiParkingGent( val name: String,
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
                          val categorie: String) {
}
fun List<ApiParkingGent>.asDomainObjects(): List<ParkingInfo> {
    var domainList = this.map {
        ParkingInfo(it.name, it.lastupdate, it.totalcapacity, it.availablecapacity, it.occupation, it.type, it.description, it.id, it.openingtimesdescription,
            it.isopennow, it.temporaryclosed, it.operatorinformation, it.freeparking, it.urllinkaddress, it.occupancytrend, Json.decodeFromString<LocationAndDimension>(it.locationanddimension), it.location, it.text, it.categorie)
    }
    return domainList
}