package com.example.parkinggent.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.parkinggent.R
import com.example.parkinggent.ui.theme.AppTheme

/**
 * Composable function to display an error message.
 *
 * @param modifier Modifier for this composable, used to modify its appearance or behavior.
 * @param errorMessage The error message text to be displayed.
 */
@Composable
fun ErrorComponent(
    modifier: Modifier = Modifier,
    errorMessage: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Error,
            contentDescription = stringResource(id = R.string.ErrorComponent_ErrorDescription),
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

/**
 * Preview for the ErrorComponent.
 */
@Preview(showBackground = true)
@Composable
fun ErrorComponentPreview() {
    AppTheme {
        ErrorComponent(errorMessage = stringResource(id = R.string.loadingError))
    }
}