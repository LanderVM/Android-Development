package com.example.parkinggent.ui.screens.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.parkinggent.model.Parking
import com.example.parkinggent.ui.theme.ProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingCard(parking: Parking, modifier: Modifier = Modifier, navigateToAbout: () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = { navigateToAbout() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = parking.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = parking.location,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )
                }
                Column(
                    modifier = Modifier
                        .width(150.dp)
                        .padding(top = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Occupied(parking)
                }
            }
        }
    }
}


@Composable
fun Occupied(parking: Parking){
    Row{
        Text(
            text = "${parking.used}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.blue),
        )
        Text(
            text = "/${parking.total}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
    LinearProgressIndicator(
        progress = parking.used.toFloat() / parking.total.toFloat(),
        color = colorResource(id = R.color.blue),
        modifier = Modifier
            .height(10.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}
@Preview
@Composable
fun ParkingCardPreview(){
    ProjectTheme {
        ParkingCard(
            parking = Parking(
                title = "B-Park Dampoort",
                location="Vrijdagmarkt 1\n9000 Gent",
                used=30,
                total=596,
                description = "",
                cords="",
                openingTime = "",
                open= true,
                pay= true,
                phoneNumber = "",
                moreInfo = ""
            ),
            navigateToAbout = {}
        )
    }
}

