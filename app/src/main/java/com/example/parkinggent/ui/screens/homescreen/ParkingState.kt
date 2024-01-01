package com.example.parkinggent.ui.screens.homescreen

import com.example.parkinggent.model.ParkingInfo

data class ParkingListState(val parkingList: List<ParkingInfo> = listOf())

data class ParkingState(val parking: ParkingInfo = ParkingInfo())

sealed interface ParkingApiState {
    object Success : ParkingApiState
    object Error : ParkingApiState
    object Loading : ParkingApiState
}
