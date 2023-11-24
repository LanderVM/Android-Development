package com.example.parkinggent.model

import java.time.LocalDateTime
data class Parking(
    val title: String = "",
    val location: String = "",
    val used: Int = -1,
    val total: Int = -1,
    val description: String = "",
    val cords: String = "",
    val openingTime: String = "",
    val open: Boolean = false,
    val pay : Boolean = true,
    val phoneNumber: String = "",
    val moreInfo: String = ""
)

data class ParkingInfo(
    val name: String,
    val lastupdate: LocalDateTime,
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
    val locationanddimension: LocationAndDimension,
    val location: Location,
    val text: String?,
    val categorie: String
)

data class Location(
    val lon: Double,
    val lat: Double
)

data class LocationAndDimension(
    val specificAccessInformation: List<String>,
    val level: String,
    val roadNumber: String,
    val roadName: String,
    val contactDetailsTelephoneNumber: String,
    val coordinatesForDisplay: Coordinates
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)
