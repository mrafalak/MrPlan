package com.mr.presentation.debug

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mr.common.base.navigation.AndroidScreen

class DebugScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        Text(text = "Hello in DebugScreen", style = MaterialTheme.typography.titleLarge)
    }
}