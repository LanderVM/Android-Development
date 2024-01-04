package com.example.parkinggent.ui.screens.detailscreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
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

/**
 * ViewModel associated with the DetailScreen, responsible for fetching and managing the parking spot's details.
 * It interacts with the repository to retrieve parking information and handles user actions such as calling or navigating.
 *
 * @param parkingRepository The repository used to fetch parking details.
 */
class DetailViewModel(private val parkingRepository: ParkingRepository) : ViewModel() {
    private val _parkingDetailState = MutableStateFlow<ParkingInfo?>(null)
    val parkingDetailState: StateFlow<ParkingInfo?> = _parkingDetailState.asStateFlow()

    private val _parkingApiState = MutableStateFlow<ParkingApiState>(ParkingApiState.Loading)
    val parkingApiState: StateFlow<ParkingApiState> = _parkingApiState.asStateFlow()

    /**
     * Retrieves detailed information about a specific parking spot by its ID.
     * It updates the state of the ViewModel based on the success or failure of the data fetch.
     *
     * @param parkingId The ID of the parking spot whose details are to be fetched.
     */
    fun getParkingDetails(parkingId: String) {
        viewModelScope.launch {
            _parkingApiState.value = ParkingApiState.Loading
            try {
                val parkingDetails = parkingRepository.getParking(parkingId).firstOrNull()
                _parkingDetailState.value = parkingDetails
                _parkingApiState.value = if (parkingDetails != null) ParkingApiState.Success else ParkingApiState.Error
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error fetching parking details: ${e.message}")
                _parkingApiState.value = ParkingApiState.Error
            }
        }
    }

    /**
     * Parses a string containing contact details to extract phone numbers.
     * Utilizes regular expressions to find phone numbers and associated labels within the string.
     *
     * @param contactDetails String containing the contact details to be parsed.
     * @return A map of extracted phone numbers and their labels.
     */
    fun getTelephoneNumbers(contactDetails: String?): Map<String, String> {
        val pattern = "Tel\\.:\\s([0-9 ]+)([^T]*(?=Tel\\.:|$))".toRegex()
        return pattern.findAll(contactDetails.toString()).associate {
            it.groupValues[1].trim() to it.groupValues[2].trim()
        }
    }

    /**
     * Initiates a phone call to the given phone number.
     * Uses the native phone dialer of the device, allowing the user to place a call.
     *
     * @param phoneNumber The phone number to dial.
     * @param context The current context, used to start the dialer activity.
     */
    fun callNumber(phoneNumber: String, context: Context) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${phoneNumber}")
        }
        context.startActivity(intent)
    }

    /**
     * Opens Google Maps at the specified latitude and longitude coordinates.
     * This function can be used to show the location of the parking spot on the map.
     *
     * @param context The current context, used to start the Google Maps activity.
     * @param latitude The latitude coordinate of the location.
     * @param longitude The longitude coordinate of the location.
     */
    fun openGoogleMaps(context: Context, latitude: Double, longitude: Double) {
        val uri = java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    }

    /**
     * Opens a web URL in the user's default web browser.
     * Can be used to provide more information about the parking spot or related services.
     *
     * @param context The current context, used to start the web browser activity.
     * @param url The web URL to be opened.
     */
    fun openUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        context.startActivity(intent)
    }

    /**
     * Factory for creating instances of the DetailViewModel.
     * Ensures that the ViewModel is supplied with necessary dependencies.
     */
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