package com.example.parkinggent.ui.screens.detailscreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.parkinggent.model.Coordinates
import com.example.parkinggent.model.Location
import com.example.parkinggent.model.LocationAndDimension
import com.example.parkinggent.model.ParkingInfo
import java.util.Locale

class DetailViewModel : ViewModel() {

    fun getTelephoneNumbers(contactDetails: String?): Map<String, String> {
        val pattern = "Tel\\.:\\s([0-9 ]+)([^T]*(?=Tel\\.:|$))".toRegex()
        return pattern.findAll(contactDetails.toString()).associate {
            it.groupValues[1].trim() to it.groupValues[2].trim()
        }
    }

    fun callNumber(phoneNumber: String, context: Context) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${phoneNumber}")
        }
        context.startActivity(intent)
    }

    fun openGoogleMaps(context: Context, latitude: Double, longitude: Double) {
        val uri = java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    }

    fun openUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        context.startActivity(intent)
    }

    fun getParkingInfoById(parkingId: String): ParkingInfo {
        return ParkingInfo(
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
    }
}