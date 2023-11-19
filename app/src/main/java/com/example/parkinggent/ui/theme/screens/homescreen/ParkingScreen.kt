package com.example.project.ui.screens.homescreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parkinggent.ui.screens.homescreen.ParkingViewmodel

@Composable
fun ParkingScreen(parkingViewmodel: ParkingViewmodel = viewModel(), navigateToAbout: () -> Unit){
    val taskOverviewState by parkingViewmodel.uiState.collectAsState()
    LazyColumn {
        items(taskOverviewState.parkingList) {
                parking ->
            com.example.parkinggent.ui.screens.homescreen.ParkingCard(
                parking = parking,
                navigateToAbout = navigateToAbout
            )
        }
    }
}

