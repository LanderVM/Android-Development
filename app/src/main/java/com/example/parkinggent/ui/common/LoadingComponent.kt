package com.example.parkinggent.ui.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.parkinggent.ui.theme.AppTheme

@Composable
fun LoadingComponent(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.size(50.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingComponentPreview() {
    AppTheme {
        LoadingComponent()
    }
}