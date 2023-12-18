package com.example.parkinggent.ui.screens.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingComponent(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.size(50.dp),
        color = MaterialTheme.colorScheme.primary
    )
}
