package com.mr.presentation.main

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.mr.presentation.R
import com.mr.presentation.home.base.HomeScreen
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.providers.LocalMrNavigator
import com.mr.presentation.navigation.providers.MrNavigatorProvider
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.ui.animations.DefaultFadeTransition
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainNavigator(
    initialScreen: AndroidScreen,
    viewModel: MainViewModel
) {
    MrNavigatorProvider(initialScreen = initialScreen) { navigator ->
        HandleMainEffect(viewModel = viewModel)
        DefaultFadeTransition(navigator)
    }
}

@Composable
private fun HandleMainEffect(viewModel: MainViewModel) {
    val navigator = LocalMrNavigator.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { mainEffect ->
            when (mainEffect) {
                is MainEffect.NavigateToHomeScreen -> {
                    handleNavigateToHomeScreen(
                        context = context,
                        navigator = navigator,
                        isUserLogged = state.isUserLogged,
                        deepLinkFullPath = mainEffect.deepLink.fullPath
                    )
                }
            }
        }
    }
}

private fun handleNavigateToHomeScreen(
    context: Context,
    navigator: MrNavigator,
    isUserLogged: Boolean,
    deepLinkFullPath: String
) {
    // TODO clean code
    val isHomeScreenInStack: Boolean = navigator.items.any { screen ->
        screen is HomeScreen
    }

    when {
        isHomeScreenInStack -> {
            println("DeepLink - MainNavigator handle deep link - HomeScreen in stack")
            // HomeViewModel handle deep link
            navigator.noDebounce.popUntil { screen ->
                screen is HomeScreen
            }
        }

        isUserLogged -> {
            println("DeepLink - MainNavigator handle deep link - HomeScreen not in stack")
            navigator.noDebounce.push(HomeScreen(deepLinkFullPath))
        }

        else -> {
            val message = context.getString(R.string.toast_login_to_continue)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}