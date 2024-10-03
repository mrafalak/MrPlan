package com.mr.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mr.presentation.ui.theme.MrPlanTheme

@Composable
fun MrButton(
    modifier: Modifier = Modifier,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ),
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .then(modifier),
        onClick = onClick,
        colors = buttonColors
    ) {
        Text(text = text, style = textStyle)
    }
}

@Preview
@Composable
fun MrButtonPreviewLight(modifier: Modifier = Modifier) {
    MrPlanTheme(darkTheme = false, dynamicColor = false) {
        MrButton(text = "Text") {}
    }
}

@Preview
@Composable
fun MrButtonPreviewDark(modifier: Modifier = Modifier) {
    MrPlanTheme(darkTheme = true, dynamicColor = false) {
        MrButton(text = "Text") {}
    }
}