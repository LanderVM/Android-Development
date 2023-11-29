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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parkinggent.R
import com.example.parkinggent.model.Location
import com.example.parkinggent.model.ParkingInfo
import com.example.parkinggent.ui.theme.AppTheme
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingCard(parking: ParkingInfo, modifier: Modifier = Modifier, navigateToAbout: () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.small,
        onClick = { navigateToAbout() },
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
                        text = "${parking.location.lon}, ${parking.location.lat}" ,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                    )
                }
                Column(
                    modifier = modifier
                        .width(150.dp)
                        .padding(top = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Occupied(parking, modifier)
                }
            }
        }
    }
}


@Composable
fun Occupied(parking: ParkingInfo, modifier: Modifier = Modifier){
    Row{
        Text(
            text = "${parking.occupation}",
            color = colorResource(id = R.color.green),
            //color = MaterialTheme.colorScheme.onTertiary
        )
        Text(
            text = "/${parking.totalcapacity}"
        )
    }
    LinearProgressIndicator(
        progress = parking.occupation.toFloat() / parking.totalcapacity.toFloat(),
        color = colorResource(id = R.color.green),
        modifier = modifier
            .height(10.dp)
            .clip(MaterialTheme.shapes.small)
    )
}
@Preview
@Composable
fun ParkingCardPreview(){
    AppTheme {
        ParkingCard(
           parking= ParkingInfo(
                name = "Tolhuis",
                lastupdate = "2023-11-29T01:17:11",
                totalcapacity = 150,
                availablecapacity = 72,
                occupation = 52,
                type = "offStreetParkingGround",
                description = "Ondergrondse parkeergarage Tolhuis in Gent",
                id = "https://stad.gent/nl/mobiliteit-openbare-werken/parkeren/parkings-gent/parking-tolhuis",
                openingtimesdescription = "24/7",
                isopennow = 1,
                temporaryclosed = 0,
                operatorinformation = "Mobiliteitsbedrijf Gent",
                freeparking = 0,
                urllinkaddress = "https://stad.gent/nl/mobiliteit-openbare-werken/parkeren/parkings-gent/parking-tolhuis",
                occupancytrend = "unknown",
                locationanddimension = "{\"specificAccessInformation\": [\"inrit\"], \"level\": \"0\", \"roadNumber\": \"?\", \"roadName\": \"Vrijdagmarkt 1\\n9000 Gent\", \"contactDetailsTelephoneNumber\": \"Tel.: 09 266 29 00\\n(permanentie)\\nTel.: 09 266 29 01\\n(tijdens kantooruren)\", \"coordinatesForDisplay\": {\"latitude\": 51.05713405953412, \"longitude\": 3.726071777876147}}",
                location = Location(
                    lon = 3.724968367281895,
                    lat = 51.0637023559265
                ),
                text = null,
                categorie = "parking in LEZ"
            ),
            navigateToAbout = {}
        )
    }
}

