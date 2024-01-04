package com.example.parkinggent.fake

import com.example.parkinggent.data.ParkingRepository
import com.example.parkinggent.model.Coordinates
import com.example.parkinggent.model.Location
import com.example.parkinggent.model.LocationAndDimension
import com.example.parkinggent.model.ParkingInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json

class ParkingRepositoryMock : ParkingRepository {
    private val mockParkingList = listOf(
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
        ),
        ParkingInfo(
            name = "Savaanstraat",
            lastupdate = "2024-01-04T22:45:11",
            totalcapacity = 382,
            availablecapacity = 62,
            occupation = 26,
            type = "carPark",
            description = "Ondergrondse parkeergarage Savaanstraat in Gent",
            id = "https://stad.gent/nl/mobiliteit-openbare-werken/parkeren/parkings-gent/parking-savaanstraat",
            openingtimesdescription = "24/7",
            isopennow = true,
            temporaryclosed = false,
            operatorinformation = "Mobiliteitsbedrijf Gent",
            freeparking = false,
            urllinkaddress = "https://stad.gent/nl/mobiliteit-openbare-werken/parkeren/parkings-gent/parking-savaanstraat",
            occupancytrend = "unknown",
            locationanddimension = LocationAndDimension(
                specificAccessInformation = listOf("inrit"),
                level = "0",
                roadNumber = "?",
                roadName = "Savaanstraat 13\n9000 Gent",
                contactDetailsTelephoneNumber = "Tel.: 09 266 29 40",
                coordinatesForDisplay = Coordinates(
                    latitude = 51.04877362543108,
                    longitude = 3.7234627726667133
                )
            ),
            location = Location(
                lon = 3.7234627726667133,
                lat = 51.04877362543108
            ),
            text = null,
            categorie = "parking in LEZ"
        )
    )

    override fun getParking(): Flow<List<ParkingInfo>> {
        return flowOf(mockParkingList)
    }

    override fun getParking(id: String): Flow<ParkingInfo?> {
        return flowOf(mockParkingList.find { it.id == id })
    }


    override suspend fun insertParking(parking: ParkingInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun refresh() {
        TODO("Not yet implemented")
    }
}