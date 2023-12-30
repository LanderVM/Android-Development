package com.example.parkinggent.ui.screens.homescreen

import com.example.parkinggent.model.ParkingInfo

data class ParkingState(
    val parkingList: List<ParkingInfo>
)
data class ParkingListState(val parkingList: List<ParkingInfo> = listOf())

sealed interface ParkingApiState {
    object Success : ParkingApiState
    object Error : ParkingApiState
    object Loading : ParkingApiState
}
