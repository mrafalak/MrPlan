package com.mr.presentation.home.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import com.mr.presentation.home.goal.GoalDetailsScreen
import com.mr.presentation.home.goal.GoalEffect
import com.mr.presentation.home.goal.GoalViewModel
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.NavigatorImpl
import com.mr.presentation.navigation.NavigatorNoOp
import com.mr.presentation.ui.animations.DefaultSlideTransition
import kotlinx.coroutines.flow.collectLatest

val LocalHomeNavigator: ProvidableCompositionLocal<MrNavigator> =
    staticCompositionLocalOf { NavigatorNoOp() }

@Composable
fun HomeNavigator(
    initialScreen: AndroidScreenHome,
    content: @Composable (homeNavigator: MrNavigator) -> Unit = {}
) {
    Navigator(initialScreen) { navigator ->
        val homeNavigator = remember(navigator) {
            NavigatorImpl(navigator)
        }

        CompositionLocalProvider(
            LocalHomeNavigator provides homeNavigator
        ) {
            DefaultSlideTransition(navigator)
            content(homeNavigator)
        }
    }
}