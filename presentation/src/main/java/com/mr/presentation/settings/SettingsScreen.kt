package com.mr.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mr.presentation.ui.AndroidScreen

class SettingsScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        Scaffold { innerPaddings ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPaddings)
            ) {
                Text(
                    text = "Hello in Settings Screen",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}