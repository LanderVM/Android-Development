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

/**
 * The main activity for the Parking Gent application.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState.
     *                           Otherwise, it is null.
     */
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

/**
 * Preview for the ParkingApp with a dark theme.
 */
@Preview
@Composable
fun ParkingAppDarkThemePreview() {
    AppTheme(darkTheme = true) {
        ParkingApp()
    }
}

/**
 * Preview for the ParkingApp with a light theme.
 */
@Preview
@Composable
fun ParkingAppLightThemePreview() {
    AppTheme(darkTheme = false) {
        ParkingApp()
    }
}
