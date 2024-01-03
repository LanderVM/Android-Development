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
import com.example.parkinggent.ui.screens.homescreen.ParkingApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Locale

class DetailViewModel(private val parkingRepository: ParkingRepository) : ViewModel() {
    private val _parkingDetailState = MutableStateFlow<ParkingInfo?>(null)
    val parkingDetailState: StateFlow<ParkingInfo?> = _parkingDetailState.asStateFlow()

    // Add this line
    private val _parkingApiState = MutableStateFlow<ParkingApiState>(ParkingApiState.Loading)
    val parkingApiState: StateFlow<ParkingApiState> = _parkingApiState.asStateFlow()

    fun getParkingDetails(parkingId: String) {
        viewModelScope.launch {
            _parkingApiState.value = ParkingApiState.Loading
            try {
                val parkingDetails = parkingRepository.getParking(parkingId).firstOrNull()
                _parkingDetailState.value = parkingDetails
                _parkingApiState.value = if (parkingDetails != null) ParkingApiState.Success else ParkingApiState.Error
            } catch (e: Exception) {
                _parkingApiState.value = ParkingApiState.Error
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