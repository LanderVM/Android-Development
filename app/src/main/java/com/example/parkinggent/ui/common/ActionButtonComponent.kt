package com.example.parkinggent.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.parkinggent.R
import com.example.parkinggent.ui.theme.AppTheme

/**
 * Composable function for an Action Button.
 *
 * @param onClick The action to perform when the button is clicked.
 * @param text The text to be displayed on the button.
 */
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

/**
 * Preview for the ActionButtonComponent.
 */
@Preview(showBackground = true)
@Composable
fun ActionButtonComponentPreview() {
    AppTheme {
        ActionButton(onClick = {}, text = stringResource(id = R.string.moreInfo))
    }
}