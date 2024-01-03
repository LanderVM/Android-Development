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

class ParkingViewModel(private val parkingRepository: ParkingRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ParkingListState())
    val uiState: StateFlow<ParkingListState> = _uiState.asStateFlow()
    var parkingApiState: ParkingApiState by mutableStateOf(ParkingApiState.Loading)
        private set

    init {
        getRepoParkings()
    }

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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ParkingApplication)
                val parkingRepository = application.container.parkingRepository
                ParkingViewModel(parkingRepository = parkingRepository)
            }
        }
    }

    fun sortParkingsByName() {
        _uiState.update { currentState ->
            currentState.copy(parkingList = currentState.parkingList.sortedBy { it.name })
        }
    }

    fun sortParkingsByFreePlaces() {
        _uiState.update { currentState ->
            currentState.copy(parkingList = currentState.parkingList.sortedByDescending { it.availablecapacity })
        }
    }
}