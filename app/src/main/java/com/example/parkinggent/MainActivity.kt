package com.example.parkinggent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.parkinggent.ui.ParkingApp
import com.example.parkinggent.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    ParkingApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun ParkingAppDarkThemePreview() {
    AppTheme(darkTheme = true) {
        ParkingApp()
    }
}
@Preview
@Composable
fun ParkingAppLightThemePreview() {
    AppTheme(darkTheme = false) {
        ParkingApp()
    }
}
