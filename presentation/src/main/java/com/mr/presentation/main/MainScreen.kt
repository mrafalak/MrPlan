package com.mr.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import com.mr.presentation.BuildConfig
import com.mr.presentation.home.base.HomeScreen
import com.mr.presentation.ui.theme.MrPlanTheme
import com.mr.presentation.welcome.WelcomeScreen

@Composable
fun MainScreen(
    initialScreen: Screen? = null,
) {
    MrPlanTheme {
        val screen = remember {
            when {
                BuildConfig.DEBUG -> HomeScreen()
                initialScreen != null -> initialScreen
                else -> WelcomeScreen()
            }
        }

        MainNavigator(initialScreen = screen)
    }
}