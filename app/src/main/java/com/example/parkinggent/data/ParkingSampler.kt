package com.example.parkinggent.data

import com.example.parkinggent.model.Parking

object ParkingSampler{
    val sampleParking = mutableListOf(
        Parking("B-Park Dampoort", "Vrijdagmarkt 1\n9000 Gent", 30, 596, "", "", "", true,true, "", ""),
        Parking("Sint-Pietersplein", "Sint-Pietersplein 65\n9000 Gent", 430, 551, "", "", "", true, true,"", ""),
        Parking("Sint-Pietersplein2", "Sint-Pietersplein2 64\n9000 Gent", 230, 501, "", "", "", true, true,"", ""),
        Parking("Sint-Pietersplein2", "Sint-Pietersplein2 64\n9000 Gent", 230, 501, "", "", "", true, true,"", ""),
        Parking("Sint-Pietersplein2", "Sint-Pietersplein2 64\n9000 Gent", 230, 501, "", "", "", true,true, "", ""),
        Parking("Sint-Pietersplein2", "Sint-Pietersplein2 64\n9000 Gent", 230, 501, "", "", "", true,true, "", ""),
        Parking("Sint-Pietersplein2", "Sint-Pietersplein2 64\n9000 Gent", 230, 501, "", "", "", true, true,"", ""),
        Parking("Sint-Pietersplein2", "Sint-Pietersplein2 64\n9000 Gent", 230, 501, "", "", "", true, true,"", ""),
        Parking("Sint-Pietersplein2", "Sint-Pietersplein2 64\n9000 Gent", 230, 501, "", "", "", true, true,"", ""),
        Parking("Sint-Pietersplein2", "Sint-Pietersplein2 64\n9000 Gent", 230, 501, "", "", "", true, true,"", "")
    )
    val getAll: () -> List<Parking> = {
        val list = mutableListOf<Parking>()
        for (item in sampleParking) {
            list.add(item)
        }
        list
    }
}