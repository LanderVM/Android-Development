package com.example.parkinggent.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.parkinggent.model.ParkingInfo

@Entity(tableName = "parking")
data class dbParking(
    @PrimaryKey
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
    val text: String? = "",
    val categorie: String
)

@Entity(tableName = "location")
data class Location(
    val lon: Double,
    val lat: Double
)

@Entity(tableName = "locationAndDimension")
data class LocationAndDimension(
    val specificAccessInformation: List<String>,
    val level: String,
    val roadNumber: String,
    val roadName: String,
    val contactDetailsTelephoneNumber: String? = null,
    val coordinatesForDisplay: Coordinates
)

@Entity(tableName = "coordinates")
data class Coordinates(
    val latitude: Double,
    val longitude: Double
)

fun dbParking.asDomainParking(): ParkingInfo {
    return ParkingInfo(
        this.name,
        this.lastupdate,
        this.totalcapacity,
        this.availablecapacity,
        this.occupation,
        this.type,
        this.description,
        this.id,
        this.openingtimesdescription,
        this.isopennow,
        this.temporaryclosed,
        this.operatorinformation,
        this.freeparking,
        this.urllinkaddress,
        this.occupancytrend,
        com.example.parkinggent.model.LocationAndDimension(
            this.locationanddimension.specificAccessInformation,
            this.locationanddimension.level,
            this.locationanddimension.roadNumber,
            this.locationanddimension.roadName,
            this.locationanddimension.contactDetailsTelephoneNumber,
            com.example.parkinggent.model.Coordinates(
                this.locationanddimension.coordinatesForDisplay.latitude,
                this.locationanddimension.coordinatesForDisplay.longitude,
            )
        ),
        com.example.parkinggent.model.Location(
            this.location.lon,
            this.location.lat
        ),
        this.text,
        this.categorie
    )
}

fun ParkingInfo.asDbParking(): dbParking {
    return dbParking(
        name = this.name,
        lastupdate = this.lastupdate,
        totalcapacity = this.totalcapacity,
        availablecapacity = this.availablecapacity,
        occupation = this.occupation,
        type = this.type,
        description = this.description,
        id = this.id,
        openingtimesdescription = this.openingtimesdescription,
        isopennow = this.isopennow,
        temporaryclosed = this.temporaryclosed,
        operatorinformation = this.operatorinformation,
        freeparking = this.freeparking,
        urllinkaddress = this.urllinkaddress,
        occupancytrend = this.occupancytrend,
        locationanddimension = LocationAndDimension(
            specificAccessInformation = this.locationanddimension.specificAccessInformation,
            level = this.locationanddimension.level,
            roadNumber = this.locationanddimension.roadNumber,
            roadName = this.locationanddimension.roadName,
            contactDetailsTelephoneNumber = this.locationanddimension.contactDetailsTelephoneNumber,
            coordinatesForDisplay = Coordinates(
                latitude = this.locationanddimension.coordinatesForDisplay.latitude,
                longitude = this.locationanddimension.coordinatesForDisplay.longitude
            )
        ),
        location = Location(lon = this.location.lon, lat = this.location.lat),
        text = this.text,
        categorie = this.categorie
    )
}

fun List<dbParking>.asDomainParkings(): List<ParkingInfo> {
    var taskList = this.map {
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
            it.isopennow,
            it.temporaryclosed,
            it.operatorinformation,
            it.freeparking,
            it.urllinkaddress,
            it.occupancytrend,
            com.example.parkinggent.model.LocationAndDimension(
                it.locationanddimension.specificAccessInformation,
                it.locationanddimension.level,
                it.locationanddimension.roadNumber,
                it.locationanddimension.roadName,
                it.locationanddimension.contactDetailsTelephoneNumber,
                com.example.parkinggent.model.Coordinates(it.locationanddimension.coordinatesForDisplay.latitude, it.locationanddimension.coordinatesForDisplay.longitude)
            ),
            com.example.parkinggent.model.Location(it.location.lon, it.location.lat),
            it.text,
            it.categorie
        )
    }
    return taskList
}