package com.example.parkinggent.ui.screens.homescreen

import ErrorComponent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parkinggent.R
import com.example.parkinggent.ui.common.LoadingComponent

@Composable
fun ParkingScreen(
    modifier: Modifier = Modifier,
    parkingViewmodel: ParkingViewmodel = viewModel(factory = ParkingViewmodel.Factory),
    navigateToAbout: (String) -> Unit
) {
    val parkingState by parkingViewmodel.uiState.collectAsState()
    val parkingApiState = parkingViewmodel.parkingApiState
    var selectedSortOption by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            SortButtons(
                selectedSortOption = selectedSortOption,
                onSortOptionSelected = { option ->
                    selectedSortOption = option
                },
                parkingViewmodel = parkingViewmodel,
            )
        }

        Box(
            modifier = Modifier
                .weight(1f, fill = true)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when (parkingApiState) {
                is ParkingApiState.Loading -> LoadingComponent()
                is ParkingApiState.Error -> ErrorComponent(errorMessage = stringResource(id = R.string.loadingError))
                is ParkingApiState.Success -> ParkingListComponent(
                    parkingState = parkingState,
                    navigateToAbout = navigateToAbout
                )
            }
        }
    }
}

@Composable
fun ParkingListComponent(
    modifier: Modifier = Modifier,
    parkingState: ParkingState,
    navigateToAbout: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(parkingState.parkingList) { parking ->
            ParkingCard(parking = parking, navigateToAbout = navigateToAbout)
        }
    }
}


@Composable
fun SortButtons(
    selectedSortOption: String,
    onSortOptionSelected: (String) -> Unit,
    parkingViewmodel: ParkingViewmodel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        SortButton(
            text = stringResource(id = R.string.parkingScreen_SortName),
            isSelected = selectedSortOption == "Name",
            onClick = {
                onSortOptionSelected("Name")
                parkingViewmodel.sortParkingsByName()
            }
        )
        Spacer(Modifier.width(8.dp))
        SortButton(
            text = stringResource(id = R.string.parkingScreen_SortFreeSpaces),
            isSelected = selectedSortOption == "Free Places",
            onClick = {
                onSortOptionSelected("Free Places")
                parkingViewmodel.sortParkingsByFreePlaces()
            }
        )
    }
}

@Composable
fun SortButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
        )
    ) {
        Text(text)
    }
}

