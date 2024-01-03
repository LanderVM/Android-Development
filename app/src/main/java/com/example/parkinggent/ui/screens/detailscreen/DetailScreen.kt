package com.example.parkinggent.ui.screens.detailscreen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parkinggent.R
import com.example.parkinggent.data.ParkingSampler
import com.example.parkinggent.model.ParkingInfo
import com.example.parkinggent.ui.screens.homescreen.ParkingApiState
import com.example.parkinggent.ui.theme.AppTheme

@Composable
fun DetailScreen(
    parkingId: String,
    detailViewModel: DetailViewModel = viewModel(factory = DetailViewModel.Factory)
) {
    val context = LocalContext.current
    val parking by detailViewModel.parkingDetailState.collectAsState()
    val parkingApiState by detailViewModel.parkingApiState.collectAsState()

    LaunchedEffect(parkingId) {
        detailViewModel.getParkingDetails(parkingId)
    }

    when (parkingApiState) {
        ParkingApiState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        ParkingApiState.Error -> Text("Error loading parking details")
        ParkingApiState.Success -> {
            parking?.let {  currentParking ->
                DetailContent(currentParking, detailViewModel, context)
            } ?: Text("No parking information available.")
        }
    }
}

@Composable
fun DetailContent(
    currentParking: ParkingInfo,
    detailViewModel: DetailViewModel,
    context: Context
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )  {
            Text(text = currentParking.name, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = currentParking.description)

            Text(text = "${stringResource(R.string.availableCapacity)}: ${currentParking.availablecapacity}")
            Text(text = "${stringResource(R.string.totalCapacity)}: ${currentParking.totalcapacity}")
            Text(text = "${stringResource(R.string.openingtimesDescription)}: ${currentParking.openingtimesdescription}")

            Text(text = currentParking.operatorinformation)

            IsOpen(currentParking)

            Text(text = stringResource(if (currentParking.freeparking) R.string.freeParking else R.string.paidParking))

            for (phone in detailViewModel.getTelephoneNumbers(currentParking.locationanddimension.contactDetailsTelephoneNumber)) {
                PhoneNumber(phone, detailViewModel::callNumber)
            }

            ActionButton(
                onClick = {
                    detailViewModel.openUrl(
                        context = context,
                        url = currentParking.urllinkaddress
                    )
                },
                text = stringResource(R.string.moreInfo)
            )
            ActionButton(
                onClick = {
                    detailViewModel.openGoogleMaps(
                        context = context,
                        latitude = currentParking.location.lat,
                        longitude = currentParking.location.lon
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
        DetailScreen(parkingId = ParkingSampler.getFirst().name)
    }
}