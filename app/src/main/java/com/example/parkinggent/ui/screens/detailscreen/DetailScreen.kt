package com.example.parkinggent.ui.screens.detailscreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.parkinggent.data.ParkingSampler
import com.example.parkinggent.model.ParkingInfo
import com.example.parkinggent.ui.theme.AppTheme

@Composable
fun DetailScreen(detailViewmodel: DetailViewmodel = viewModel(), parking: ParkingInfo){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(text = "${stringResource(R.string.description)}: ")
        Text(text = parking.description, modifier = Modifier.padding(start = 10.dp))
        Text(text = "${stringResource(R.string.distance)}: ${parking.locationanddimension.coordinatesForDisplay}")
        Text(text = "${stringResource(R.string.availableCapacity)}: ${(parking.totalcapacity - parking.occupation)}")
        Text(text = "${stringResource(R.string.totalCapacity)}: ${parking.totalcapacity}")
        Text(text = "${stringResource(R.string.openingtimesDescription)}: ${parking.openingtimesdescription}")

        IsOpen(parking)

        Text(text = stringResource(if (parking.freeparking) R.string.freeParking else R.string.paidParking))

        for(phone in detailViewmodel.getTelephoneNumbers(parking.locationanddimension.contactDetailsTelephoneNumber)) {
            PhoneNumber(phone)
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(R.string.moreInfo))
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(R.string.openRouteDescription))
        }
    }
}

@Composable
fun IsOpen(parking: ParkingInfo) {
    Row {
        Text(text = stringResource(if (parking.isopennow) R.string.isOpenNow else R.string.isClosedNow))
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
fun PhoneNumber(phone: Map.Entry<String, String>) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${phone.key}")
            }
            context.startActivity(intent)
        }
    ) {
        Icon(
            imageVector = Icons.Default.Call,
            contentDescription = "Phone Call"
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = phone.key,
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = phone.value,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    AppTheme {
        DetailScreen(parking = ParkingSampler.getFirst())
    }
}