package com.example.parkinggent.ui.screens.detailscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.parkinggent.R
import com.example.parkinggent.model.Parking
import com.example.parkinggent.ui.theme.ProjectTheme

@Composable
fun DetailScreen(parking: Parking){
    Column {
        Text(text = stringResource(R.string.beschrijving))
        Text(text = parking.description)
        Text(text = "afstand: " + parking.cords)
        Text(text = "vrije plaatsen: " + (parking.total - parking.used))
        Text(text = "Totale plaatsen: " + parking.total)
        Text(text = "Openingstijd: " + parking.openingTime)
        Text(text = "Open:" + parking.open )
        Text(text = parking.pay.toString())
    }
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    ProjectTheme {
        DetailScreen(Parking("B-Park Dampoort", "Vrijdagmarkt 1\n9000 Gent", 30, 596))
    }
}