package com.example.parkinggent.ui.screens.homescreen

import android.util.Log
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

/**
 * ViewModel for managing parking data and state in the ParkingScreen.
 * It handles fetching parking data from the repository, sorting, and error handling.
 *
 * @property parkingRepository The repository from which parking data is fetched.
 */
class ParkingViewModel(private val parkingRepository: ParkingRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ParkingListState())
    val uiState: StateFlow<ParkingListState> = _uiState.asStateFlow()

    private val _parkingApiState = MutableStateFlow<ParkingApiState>(ParkingApiState.Loading)
    val parkingApiState: StateFlow<ParkingApiState> = _parkingApiState.asStateFlow()

    private val _selectedSortOption = MutableStateFlow("")

    val selectedSortOption: StateFlow<String> = _selectedSortOption.asStateFlow()
    private val _isUserInitiatedRefresh = MutableStateFlow(false)
    val isUserInitiatedRefresh: StateFlow<Boolean> = _isUserInitiatedRefresh.asStateFlow()


    /**
     * Initializes the ViewModel by fetching parking data from the repository.
     */
    init {
        getRepoParkings()
    }

    /**
     * Refreshes parking data by resetting the state and fetching new data.
     */
    fun refresh() {
        _isUserInitiatedRefresh.value = true
        _parkingApiState.value = ParkingApiState.Loading
        getRepoParkings()
    }

    /**
     * Fetches parking data from the repository and updates the UI state.
     * Sets the parkingApiState to Success or Error based on the result.
     */
    private fun getRepoParkings() {
        viewModelScope.launch {
            _parkingApiState.value = ParkingApiState.Loading
            try {
                parkingRepository.getParking().collect { listResult ->
                    _uiState.value = ParkingListState(listResult)
                    _parkingApiState.value = ParkingApiState.Success
                    _isUserInitiatedRefresh.value = false
                }
            } catch (e: Exception) {
                Log.e("ParkingViewModel", "Error fetching parkings: ${e.message}")
                _parkingApiState.value = ParkingApiState.Error
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