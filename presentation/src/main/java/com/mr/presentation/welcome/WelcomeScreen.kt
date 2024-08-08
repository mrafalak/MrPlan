package com.mr.presentation.welcome

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mr.presentation.ui.AndroidScreen

class WelcomeScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        Text(text = "Hello in Welcome Screen", style = MaterialTheme.typography.titleLarge)
    }
}