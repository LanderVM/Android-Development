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
import com.example.parkinggent.data.ParkingSampler
import com.example.parkinggent.model.ParkingInfo
import com.example.parkinggent.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingCard(modifier: Modifier = Modifier, parking: ParkingInfo, navigateToAbout: (String) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
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
                        .width(150.dp)
                        .padding(top = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Occupied(modifier = modifier, parking= parking)
                }
            }
        }
    }
}


@Composable
fun Occupied(modifier: Modifier = Modifier, parking: ParkingInfo){
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
           parking= ParkingSampler.getFirst(),
            navigateToAbout = {}
        )
    }
}

