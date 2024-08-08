package com.mr.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.NavigatorImpl
import com.mr.presentation.navigation.NavigatorNoOp
import com.mr.presentation.ui.animations.DefaultSlideTransition

val LocalMrNavigator: ProvidableCompositionLocal<MrNavigator> =
    staticCompositionLocalOf { NavigatorNoOp() }

@Composable
fun MainNavigator(initialScreen: Screen) {
    Navigator(initialScreen) { navigator ->
        val mainNavigator = remember(navigator) {
            NavigatorImpl(navigator)
        }

        CompositionLocalProvider(
            LocalMrNavigator provides mainNavigator
        ) {
            DefaultSlideTransition(navigator)
        }
    }
}