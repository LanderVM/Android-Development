package com.example.parkinggent.ui.screens.detailscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parkinggent.R
import com.example.parkinggent.model.ParkingInfo
import com.example.parkinggent.ui.theme.AppTheme

@Composable
fun DetailScreen(parking: ParkingInfo){
    /*
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(text = "${stringResource(R.string.description)}: ")
        Text(text = parking.description)
        Text(text = "${stringResource(R.string.distance)}: ${parking.cords}")
        Text(text = "${stringResource(R.string.availableCapacity)}: ${(parking.total - parking.used)}")
        Text(text = "${stringResource(R.string.totalCapacity)}: ${parking.total}")
        Text(text = "${stringResource(R.string.openingtimesDescription)}: ${parking.openingTime}")
        Row {
            Text(text = stringResource(if (parking.open) R.string.isOpenNow else R.string.isClosedNow))
            Icon(
                imageVector = if (parking.open) Icons.Default.Check else Icons.Default.Close,
                contentDescription = if (parking.open) stringResource(R.string.isOpenNow) else stringResource(
                    R.string.isClosedNow
                ),
                tint = if (parking.open) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }
        Text(text = stringResource(if (parking.pay) R.string.freeParking else R.string.paidParking))
        Text(text = parking.phoneNumber)
        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(R.string.moreInfo))
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(R.string.openRouteDescription))
        }
    }*/
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    AppTheme {
        //DetailScreen(Parking("B-Park Dampoort", "Vrijdagmarkt 1\n9000 Gent", 30, 596, "Parking in Gent", "12.0 6.0", "24/7"))
    }
}