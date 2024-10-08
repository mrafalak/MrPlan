package com.mr.presentation.main

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.mr.domain.state.AuthState
import com.mr.presentation.R
import com.mr.presentation.home.base.HomeScreen
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.providers.LocalMrNavigator
import com.mr.presentation.navigation.providers.MrNavigatorProvider
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.ui.animations.DefaultFadeTransition
import com.mr.presentation.welcome.WelcomeScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainNavigator(
    initialScreen: AndroidScreen,
    viewModel: MainViewModel
) {
    MrNavigatorProvider(initialScreen = initialScreen) { defaultNavigator ->
        HandleMainEffect(viewModel)
        DefaultFadeTransition(defaultNavigator)
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
                        isUserLogged = state.authState is AuthState.SignedIn,
                        deepLinkFullPath = mainEffect.deepLink.fullPath
                    )
                }

                is MainEffect.NavigateToWelcomeScreen -> navigator.noDebounce.replaceAll(WelcomeScreen())
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
    val isHomeScreenInStack: Boolean = navigator.items.any { screen ->
        screen is HomeScreen
    }

    when {
        isHomeScreenInStack -> {
            navigator.noDebounce.popUntil { screen ->
                screen is HomeScreen
            }
        }

        isUserLogged -> {
            navigator.noDebounce.push(HomeScreen(deepLinkFullPath))
        }

        else -> {
            val errorMessage = context.getString(R.string.toast_login_to_continue)
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}