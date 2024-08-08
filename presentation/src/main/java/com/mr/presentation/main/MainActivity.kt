package com.mr.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.mr.presentation.ui.AndroidScreen
import dagger.hilt.android.AndroidEntryPoint

val LocalActivity = staticCompositionLocalOf<ComponentActivity> {
    error("CompositionLocal Activity not present")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent()
    }

    private fun setContent(initialScreen: AndroidScreen? = null) {
        setContent {
            CompositionLocalProvider(LocalActivity provides this) {
                MainScreen(initialScreen = initialScreen)
            }
        }
    }
}