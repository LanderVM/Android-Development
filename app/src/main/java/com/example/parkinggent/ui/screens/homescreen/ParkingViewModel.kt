package com.example.parkinggent.ui.screens.homescreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.parkinggent.ParkingApplication
import com.example.parkinggent.data.ParkingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel for managing parking data and state in the ParkingScreen.
 * It handles fetching parking data from the repository, sorting, and error handling.
 *
 * @property parkingRepository The repository from which parking data is fetched.
 */
class ParkingViewModel(private val parkingRepository: ParkingRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ParkingListState())
    val uiState: StateFlow<ParkingListState> = _uiState.asStateFlow()
    var parkingApiState: ParkingApiState by mutableStateOf(ParkingApiState.Loading)
        private set

    /**
     * Initializes the ViewModel by fetching parking data from the repository.
     */
    init {
        getRepoParkings()
    }

    /**
     * Fetches parking data from the repository and updates the UI state.
     * Sets the parkingApiState to Success or Error based on the result.
     */
    private fun getRepoParkings() {
        viewModelScope.launch {
            parkingApiState = ParkingApiState.Loading
            try {
                parkingRepository.getParking().collect { listResult ->
                    _uiState.value = ParkingListState(listResult)
                    parkingApiState = ParkingApiState.Success
                }
            } catch (e: IOException) {
                Log.e("ParkingViewModel", "Error fetching parkings: ${e.message}")
                parkingApiState = ParkingApiState.Error

            }
        }
    }

    /**
     * Factory for creating instances of the ParkingViewModel.
     * Ensures that the ViewModel is supplied with necessary dependencies.
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ParkingApplication)
                val parkingRepository = application.container.parkingRepository
                ParkingViewModel(parkingRepository = parkingRepository)
            }
        }
    }

    /**
    * Sorts the parking list by name in ascending order.
    */
    fun sortParkingsByName() {
        _uiState.update { currentState ->
            currentState.copy(parkingList = currentState.parkingList.sortedBy { it.name })
        }
    }

    /**
     * Sorts the parking list by the number of free places in descending order.
     */
    fun sortParkingsByFreePlaces() {
        _uiState.update { currentState ->
            currentState.copy(parkingList = currentState.parkingList.sortedByDescending { it.availablecapacity })
        }
    }
}