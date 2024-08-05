package com.mr.mrplan.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.mr.mrplan.BuildConfig
import com.mr.mrplan.ui.theme.MrPlanTheme
import com.mr.presentation.debug.DebugScreen

@Composable
fun MainScreen(viewModel: MainViewModel, initialScreen: Screen? = null) {

    MrPlanTheme {
        val screen = remember {
            when {
                BuildConfig.DEBUG -> DebugScreen()
                initialScreen != null -> initialScreen
                else -> TODO()
            }
        }

        Navigator(screen = screen) { navigator ->
            SlideTransition(navigator)
        }
    }
}