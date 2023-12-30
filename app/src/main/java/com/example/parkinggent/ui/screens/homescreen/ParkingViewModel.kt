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
import com.example.parkinggent.data.ParkingSampler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class ParkingViewModel(private val parkingRepository: ParkingRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ParkingState(ParkingSampler.getAll()))
    val uiState: StateFlow<ParkingState> = _uiState.asStateFlow()
    lateinit var uiListState: StateFlow<ParkingListState>
    var parkingApiState: ParkingApiState by mutableStateOf(ParkingApiState.Loading)
        private set

    init {
        getRepoParkings()
    }

    /*private fun getApiParkings(){
        viewModelScope.launch {
            parkingApiState = try{
                val listResult = parkingRepository.getParking()
                _uiState.update {
                    it.copy(parkingList = listResult)
                }
                ParkingApiState.Success(listResult)
            } catch (e: IOException){
                ParkingApiState.Error
            }

        }
    }*/
    private fun getRepoParkings(){
        try {
            viewModelScope.launch { parkingRepository.refresh() }

            uiListState = parkingRepository.getParking().map { ParkingListState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = ParkingListState()
                )
            parkingApiState = ParkingApiState.Success
            Log.i("uiListState", uiListState.toString())
        }
        catch (e: IOException){
            parkingApiState = ParkingApiState.Error
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