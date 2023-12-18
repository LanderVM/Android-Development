package com.example.parkinggent.ui.screens.homescreen

import ErrorComponent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parkinggent.R
import com.example.parkinggent.ui.screens.common.LoadingComponent

@Composable
fun ParkingScreen(
    modifier: Modifier = Modifier,
    parkingViewmodel: ParkingViewmodel = viewModel(factory = ParkingViewmodel.Factory),
    navigateToAbout: (String) -> Unit
) {
    val parkingState by parkingViewmodel.uiState.collectAsState()

    val parkingApiState = parkingViewmodel.parkingApiState

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (parkingApiState) {
            is ParkingApiState.Loading -> LoadingComponent()
            is ParkingApiState.Error -> ErrorComponent(errorMessage = stringResource(id = R.string.loadingError))
            is ParkingApiState.Success -> ParkingListComponent(parkingState = parkingState, navigateToAbout = navigateToAbout)
        }
    }
}
@Composable
fun ParkingListComponent(modifier: Modifier = Modifier, parkingState: ParkingState, navigateToAbout: (String) -> Unit){
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(parkingState.parkingList) {
                parking -> ParkingCard(parking = parking, navigateToAbout = navigateToAbout)
        }
    }
}