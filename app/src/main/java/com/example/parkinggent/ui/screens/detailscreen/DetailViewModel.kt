package com.example.parkinggent.ui.screens.detailscreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.parkinggent.ParkingApplication
import com.example.parkinggent.data.ParkingRepository
import com.example.parkinggent.model.ParkingInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class DetailViewModel(private val parkingRepository: ParkingRepository) : ViewModel() {

    private val _parkingDetailState = MutableStateFlow<ParkingInfo?>(null)
    val parkingDetailState = _parkingDetailState.asStateFlow()

    fun getParkingDetails(parkingId: String) {
        viewModelScope.launch {
            parkingRepository.getParking(parkingId).collect { parkingDetails ->
                _parkingDetailState.value = parkingDetails
            }
        }
    }

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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ParkingApplication)
                val repository =
                    application.container.parkingRepository
                DetailViewModel(
                    parkingRepository = repository,
                )
            }
        }
    }
}