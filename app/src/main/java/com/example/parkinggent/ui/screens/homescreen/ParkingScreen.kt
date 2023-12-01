package com.example.parkinggent.ui.screens.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ParkingScreen(modifier: Modifier = Modifier, parkingViewmodel: ParkingViewmodel = viewModel(factory = ParkingViewmodel.Factory), navigateToAbout: () -> Unit){
    val parkingState by parkingViewmodel.uiState.collectAsState()

    //use the ApiState
    val parkingApiState = parkingViewmodel.parkingApiState

    Box(modifier = modifier) {
        when (parkingApiState) {
            is ParkingApiState.Loading -> Text("Loading...")
            is ParkingApiState.Error -> Text("Couldn't load...")
            is ParkingApiState.Success -> ParkingListComponent(parkingState = parkingState, navigateToAbout = navigateToAbout)
        }
    }

}

@Composable
fun ParkingListComponent(modifier: Modifier = Modifier, parkingState: ParkingState, navigateToAbout: () -> Unit){
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(parkingState.parkingList) {
                parking -> ParkingCard(parking = parking, navigateToAbout = navigateToAbout)
        }
    }
}