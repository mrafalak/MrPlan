package com.mr.presentation.navigation.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.navigator.Navigator
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.NavigatorImpl
import com.mr.presentation.navigation.NavigatorNoOp
import com.mr.presentation.ui.AndroidScreen

val LocalMrNavigator: ProvidableCompositionLocal<MrNavigator> =
    staticCompositionLocalOf { NavigatorNoOp() }

@Composable
fun MrNavigatorProvider(
    initialScreen: AndroidScreen,
    content: @Composable (navigator: Navigator) -> Unit
) {
    Navigator(initialScreen) { navigator ->
        val mainNavigator = remember(navigator) {
            NavigatorImpl(navigator)
        }

        CompositionLocalProvider(
            LocalMrNavigator provides mainNavigator
        ) {
            content(navigator)
        }
    }
}