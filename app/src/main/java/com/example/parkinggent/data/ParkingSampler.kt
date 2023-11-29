package com.example.parkinggent.data

import com.example.parkinggent.model.Location
import com.example.parkinggent.model.ParkingInfo
import java.time.LocalDateTime

object ParkingSampler{
    val sampleParking = mutableListOf(
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
            isopennow = 1,
            temporaryclosed = 0,
            operatorinformation = "Mobiliteitsbedrijf Gent",
            freeparking = 0,
            urllinkaddress = "https://stad.gent/nl/mobiliteit-openbare-werken/parkeren/parkings-gent/parking-tolhuis",
            occupancytrend = "unknown",
            locationanddimension = "{\"specificAccessInformation\": [\"inrit\"], \"level\": \"0\", \"roadNumber\": \"?\", \"roadName\": \"Vrijdagmarkt 1\\n9000 Gent\", \"contactDetailsTelephoneNumber\": \"Tel.: 09 266 29 00\\n(permanentie)\\nTel.: 09 266 29 01\\n(tijdens kantooruren)\", \"coordinatesForDisplay\": {\"latitude\": 51.05713405953412, \"longitude\": 3.726071777876147}}",
            location = Location(
                lon = 3.724968367281895,
                lat = 51.0637023559265
            ),
            text = null,
            categorie = "parking in LEZ"
        )
    )

    val getAll: () -> List<ParkingInfo> = {
        val list = mutableListOf<ParkingInfo>()
        for (item in sampleParking) {
            list.add(item)
        }
        list
    }
}