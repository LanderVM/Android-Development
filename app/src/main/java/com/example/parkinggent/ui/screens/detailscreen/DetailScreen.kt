package com.example.parkinggent.ui.screens.detailscreen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parkinggent.R
import com.example.parkinggent.model.ParkingInfo
import com.example.parkinggent.ui.theme.AppTheme

@Composable
fun DetailScreen(detailViewModel: DetailViewModel = viewModel(), parkingId: String) {
    val context = LocalContext.current
    val parking = detailViewModel.getParkingInfoById(parkingId)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = parking.description)

        Text(text = "${stringResource(R.string.availableCapacity)}: ${parking.availablecapacity}")
        Text(text = "${stringResource(R.string.totalCapacity)}: ${parking.totalcapacity}")
        Text(text = "${stringResource(R.string.openingtimesDescription)}: ${parking.openingtimesdescription}")

        Text(text = parking.operatorinformation)

        IsOpen(parking)

        Text(text = stringResource(if (parking.freeparking) R.string.freeParking else R.string.paidParking))

        for (phone in detailViewModel.getTelephoneNumbers(parking.locationanddimension.contactDetailsTelephoneNumber)) {
            PhoneNumber(phone, detailViewModel::callNumber)
        }

        ActionButton(
            onClick = {
                detailViewModel.openUrl(
                    context = context,
                    url = parking.urllinkaddress
                )
            },
            text = stringResource(R.string.moreInfo)
        )
        ActionButton(
            onClick = {
                detailViewModel.openGoogleMaps(
                    context = context,
                    latitude = parking.location.lat,
                    longitude = parking.location.lon
                )
            },
            text = stringResource(R.string.openRouteDescription)
        )
    }
}

@Composable
fun ActionButton(onClick: () -> Unit, text: String) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text)
    }
}


@Composable
fun IsOpen(parking: ParkingInfo) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = stringResource(if (parking.isopennow) R.string.isOpenNow else R.string.isClosedNow)
        )
        Spacer(Modifier.width(8.dp))
        Icon(
            imageVector = if (parking.isopennow) Icons.Default.Check else Icons.Default.Close,
            contentDescription = if (parking.isopennow) stringResource(R.string.isOpenNow) else stringResource(
                R.string.isClosedNow
            ),
            tint = if (parking.isopennow) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
    }
}


@Composable
fun PhoneNumber(phone: Map.Entry<String, String>, callNumber: (String, Context) -> Unit) {
    val localContext = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { callNumber(phone.key, localContext) }
            .padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Call,
            contentDescription = stringResource(id = R.string.detailScreen_PhoneIconDescription)
        )
        Spacer(Modifier.width(8.dp))
        Text(text = "${phone.key} ${phone.value}")
    }
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    AppTheme {
        //DetailScreen(parking = ParkingSampler.getFirst())
    }
}