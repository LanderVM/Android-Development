package com.example.parkinggent.ui.screens.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parkinggent.R
import com.example.parkinggent.data.ParkingSampler
import com.example.parkinggent.model.ParkingInfo
import com.example.parkinggent.ui.theme.AppTheme

/**
 * Composable function for displaying a parking card.
 *
 * @param modifier The modifier to be applied to the Card.
 * @param parking The [ParkingInfo] object containing parking spot information.
 * @param navigateToAbout A lambda function to handle navigation to the details screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingCard(
    modifier: Modifier = Modifier,
    parking: ParkingInfo,
    navigateToAbout: (String) -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(elevation = 8.dp)
            .testTag("parkingCardTag"),
        shape = MaterialTheme.shapes.small,
        onClick = { navigateToAbout(parking.name) },
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = modifier.weight(1f)
                ) {
                    Text(
                        text = parking.name,
                    )
                    Text(
                        text = parking.description,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                    )
                }
                Column(
                    modifier = modifier
                        .width(130.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Occupied(modifier = modifier, parking = parking)
                }
            }
        }
    }
}

/**
 * Composable function to display the occupancy of a parking spot.
 *
 * @param modifier The modifier to be applied to the LinearProgressIndicator.
 * @param parking The [ParkingInfo] object containing parking spot information.
 */
@Composable
fun Occupied(modifier: Modifier = Modifier, parking: ParkingInfo) {
    val occupation = parking.occupation.toFloat() / 100
    val progressIndicatorColor = when {
        occupation > 0.8 -> colorResource(R.color.red)
        occupation > 0.6 -> colorResource(R.color.orange)
        else -> colorResource(R.color.green)
    }
    Row {
        Text(
            text = "${parking.availablecapacity}",
            color = progressIndicatorColor,
        )
        Text(
            text = "/${parking.totalcapacity}"
        )
    }

    LinearProgressIndicator(
        progress = 1.toFloat() - occupation,
        color = progressIndicatorColor,
        trackColor = colorResource(id = R.color.lightGray),
        modifier = modifier
            .height(8.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

/**
 * Preview for the ParkingCard.
 */
@Preview
@Composable
fun ParkingCardPreview() {
    AppTheme {
        ParkingCard(
            parking = ParkingSampler.getFirst(),
            navigateToAbout = {}
        )
    }
}

