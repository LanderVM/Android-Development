package com.example.parkinggent.data

import com.example.parkinggent.model.Coordinates
import com.example.parkinggent.model.Location
import com.example.parkinggent.model.LocationAndDimension
import com.example.parkinggent.model.ParkingInfo

object ParkingSampler {

    private val sampleParking = listOf(
        ParkingInfo(
            name = "Tolhuis",
            lastupdate = "2023-11-29T01:17:11",
            totalcapacity = 150,
            availablecapacity = 72,
            occupation = 52,
            type = "offStreetParkingGround",
            description = "Ondergrondse parkeergarage Tolhuis in Gent",
            id = "https://stad.gent/nl/mobiliteit-openbare-werken/parkeren/parkings-gent/parking-tolhuis",
            openingtimesdescription = "24/7",
            isopennow = true,
            temporaryclosed = false,
            operatorinformation = "Mobiliteitsbedrijf Gent",
            freeparking = false,
            urllinkaddress = "https://stad.gent/nl/mobiliteit-openbare-werken/parkeren/parkings-gent/parking-tolhuis",
            occupancytrend = "unknown",
            locationanddimension = LocationAndDimension(
                specificAccessInformation = listOf("inrit"),
                level = "0",
                roadNumber = "?",
                roadName = "Vrijdagmarkt 1 \n9000 Gent",
                contactDetailsTelephoneNumber = "Tel.: 09 266 29 00 \n(permanentie)\nTel.: 09 266 29 01\n(tijdens kantooruren)",
                coordinatesForDisplay = Coordinates(
                    latitude = 51.05713405953412,
                    longitude = 3.726071777876147
                )
            ),
            location = Location(
                lon = 3.724968367281895,
                lat = 51.0637023559265
            ),
            text = null,
            categorie = "parking in LEZ"
        )
    )
    fun getFirst(): ParkingInfo = sampleParking.firstOrNull() ?: ParkingInfo()
}