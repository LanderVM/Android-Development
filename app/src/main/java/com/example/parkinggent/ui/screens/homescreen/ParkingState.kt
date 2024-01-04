package com.example.parkinggent.ui.screens.homescreen

import com.example.parkinggent.model.ParkingInfo

/**
 * Represents the state of the parking list.
 *
 * @property parkingList The list of parking information.
 */
data class ParkingListState(val parkingList: List<ParkingInfo> = listOf())

/**
 * Represents the possible states of an API call in the Parking API.
 */
sealed interface ParkingApiState {
    object Success : ParkingApiState
    object Error : ParkingApiState
    object Loading : ParkingApiState
}
