package com.example.parkinggent.ui.screens.detailscreen

import androidx.lifecycle.ViewModel
import com.example.parkinggent.data.ParkingSampler
import com.example.parkinggent.ui.screens.homescreen.ParkingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewmodel: ViewModel() {

    fun getTelephoneNumbers(contactDetails: String?): Map<String, String> {
        val pattern = "Tel\\.:\\s([0-9 ]+)([^T]*(?=Tel\\.:|$))".toRegex()
        return pattern.findAll(contactDetails.toString()).associate {
            it.groupValues[1].trim() to it.groupValues[2].trim()
        }
    }

}