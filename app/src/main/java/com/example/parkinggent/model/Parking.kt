package com.example.parkinggent.model

import kotlinx.serialization.Serializable

data class ParkingInfo(
    val name: String,
    val lastupdate: String,
    val totalcapacity: Int,
    val availablecapacity: Int,
    val occupation: Int,
    val type: String,
    val description: String,
    val id: String,
    val openingtimesdescription: String,
    val isopennow: Boolean,
    val temporaryclosed: Boolean,
    val operatorinformation: String,
    val freeparking: Boolean,
    val urllinkaddress: String,
    val occupancytrend: String,
    val locationanddimension: LocationAndDimension,
    val location: Location,
    val text: String?,
    val categorie: String
) {
    constructor() : this(
        name = "",
        lastupdate = "",
        totalcapacity = 0,
        availablecapacity = 0,
        occupation = 0,
        type = "",
        description = "",
        id = "",
        openingtimesdescription = "",
        isopennow = false,
        temporaryclosed = false,
        operatorinformation = "",
        freeparking = false,
        urllinkaddress = "",
        occupancytrend = "",
        locationanddimension = LocationAndDimension(
            specificAccessInformation = listOf(),
            level = "",
            roadNumber = "",
            roadName = "",
            contactDetailsTelephoneNumber = "",
            Coordinates(latitude = 0.0, longitude = 0.0)
        ),
        location = Location(lon = 0.0, lat = 0.0),
        text = null,
        categorie = ""
    )
}

@Serializable
data class Location(
    val lon: Double,
    val lat: Double
)

@Serializable
data class LocationAndDimension(
    val specificAccessInformation: List<String>,
    val level: String,
    val roadNumber: String,
    val roadName: String,
    val contactDetailsTelephoneNumber: String? = null,
    val coordinatesForDisplay: Coordinates
)

@Serializable
data class Coordinates(
    val latitude: Double,
    val longitude: Double
)
