package com.example.parkinggent.ui.screens.homescreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkinggent.data.ParkingSampler
import com.example.parkinggent.network.ParkingGentApi
import com.example.parkinggent.network.asDomainObjects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class ParkingViewmodel : ViewModel() {
    private val _uiState = MutableStateFlow(ParkingState(ParkingSampler.getAll()))
    val uiState: StateFlow<ParkingState> = _uiState.asStateFlow()

    var parkingApiState: ParkingApiState by mutableStateOf(ParkingApiState.Loading)
        private set

    init {
        getApiTasks()
    }

    private fun getApiTasks(){
        viewModelScope.launch {
            try{
                val listResult = ParkingGentApi.retrofitService.getParkings().results
                _uiState.update {
                    it.copy(parkingList = listResult.asDomainObjects())
                }
                parkingApiState = ParkingApiState.Success(listResult.asDomainObjects())
            }
            catch (e: IOException){
                parkingApiState = ParkingApiState.Error
            }

        }
    }
}