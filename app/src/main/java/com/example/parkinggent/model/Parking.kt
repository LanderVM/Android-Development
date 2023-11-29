package com.example.parkinggent.model

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
    val isopennow: Int,
    val temporaryclosed: Int,
    val operatorinformation: String,
    val freeparking: Int,
    val urllinkaddress: String,
    val occupancytrend: String,
    val locationanddimension: String, //LocationAndDimension
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
