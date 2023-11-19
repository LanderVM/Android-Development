package com.example.parkinggent.ui.screens.homescreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parkinggent.ui.theme.ProjectTheme

@Composable
fun ParkingScreen(parkingViewmodel: ParkingViewmodel = viewModel(), navigateToAbout: () -> Unit){
    val taskOverviewState by parkingViewmodel.uiState.collectAsState()
    LazyColumn {
        items(taskOverviewState.parkingList) {
                parking -> ParkingCard(parking = parking, navigateToAbout = navigateToAbout)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ParkingScreenPreview(){
    ProjectTheme {
        ParkingScreen(
            navigateToAbout = {}
        )
    }
}