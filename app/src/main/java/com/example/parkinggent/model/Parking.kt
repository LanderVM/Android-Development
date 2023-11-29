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
    val locationanddimension: LocationAndDimension, //LocationAndDimension
    val location: Location,
    val text: String?,
    val categorie: String
)

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
