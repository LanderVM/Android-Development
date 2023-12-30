package com.example.parkinggent.ui.screens.homescreen

import com.example.parkinggent.model.ParkingInfo

data class ParkingState(
    val parkingList: List<ParkingInfo>
)

sealed interface ParkingApiState {
    data class Success(val parkings: List<ParkingInfo>) : ParkingApiState
    object Error : ParkingApiState
    object Loading : ParkingApiState
}
