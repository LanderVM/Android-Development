package com.example.parkinggent.ui.screens.homescreen

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parkinggent.R
import com.example.parkinggent.ui.common.ErrorComponent
import com.example.parkinggent.ui.common.LoadingComponent
import com.example.parkinggent.ui.theme.AppTheme

/**
 * Composable function that represents the main screen displaying parking spots.
 *
 * @param modifier The modifier to be applied to the Column.
 * @param parkingViewModel The ViewModel providing parking data and functionality.
 * @param navigateToAbout Function to navigate to the detail screen of a parking spot.
 */
@Composable
fun ParkingScreen(
    modifier: Modifier = Modifier,
    parkingViewModel: ParkingViewModel = viewModel(factory = ParkingViewModel.Factory),
    navigateToAbout: (String) -> Unit
) {
    val parkingListState by parkingViewModel.uiState.collectAsState()
    val parkingApiState = parkingViewModel.parkingApiState
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
                parkingViewModel = parkingViewModel,
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
                    parkingState = parkingListState,
                    navigateToAbout = navigateToAbout
                )
            }
        }
    }
}

/**
 * Composable function that represents a list of parking cards.
 *
 * @param parkingState The state containing the list of parking spots to display.
 * @param navigateToAbout Function to navigate to the detail screen of a parking spot.
 */
@Composable
fun ParkingListComponent(
    parkingState: ParkingListState,
    navigateToAbout: (String) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(parkingState.parkingList) { parking ->
            ParkingCard(parking = parking, navigateToAbout = navigateToAbout)
        }
    }
}

/**
 * Composable function to display sorting buttons for parking spots.
 *
 * @param selectedSortOption The currently selected sorting option.
 * @param onSortOptionSelected Function called when a new sorting option is selected.
 * @param parkingViewModel The ViewModel providing parking data and functionality.
 */
@Composable
fun SortButtons(
    selectedSortOption: String,
    onSortOptionSelected: (String) -> Unit,
    parkingViewModel: ParkingViewModel
) {
    val sortByName = stringResource(id = R.string.parkingScreen_SortName)
    val sortByFreePlaces = stringResource(id = R.string.parkingScreen_SortFreeSpaces)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        SortButton(
            text = sortByName,
            isSelected = selectedSortOption == sortByName,
            onClick = {
                onSortOptionSelected(sortByName)
                parkingViewModel.sortParkingsByName()
            }
        )
        Spacer(Modifier.width(8.dp))
        SortButton(
            text = sortByFreePlaces,
            isSelected = selectedSortOption == sortByFreePlaces,
            onClick = {
                onSortOptionSelected(sortByFreePlaces)
                parkingViewModel.sortParkingsByFreePlaces()
            }
        )
    }
}

/**
 * Composable function for a single sorting button.
 *
 * @param text The text to display on the button.
 * @param isSelected Boolean indicating if this option is currently selected.
 * @param onClick Function called when the button is clicked.
 */
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

/**
 * Preview for the ParkingScreen.
 */
@Preview
@Composable
fun ParkingScreenPreview() {
    AppTheme {
        ParkingScreen(
            navigateToAbout = {}
        )
    }
}