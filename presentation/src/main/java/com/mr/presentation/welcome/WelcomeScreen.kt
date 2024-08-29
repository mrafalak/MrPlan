package com.mr.presentation.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mr.presentation.home.base.HomeScreen
import com.mr.presentation.main.MainEffect
import com.mr.presentation.navigation.providers.LocalMrNavigator
import com.mr.presentation.ui.AndroidScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WelcomeScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val navigator = LocalMrNavigator.current
        val viewModel: WelcomeViewModel = hiltViewModel()

        LaunchedEffect(viewModel.effect) {
            viewModel.effect.collectLatest { effect ->
                when (effect) {
                    is WelcomeEffect.NavigateToHomeScreen -> {
                        navigator.noDebounce.push(HomeScreen())
                    }
                }
            }
        }

        Scaffold { innerPaddings ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPaddings)
            ) {
                Text(
                    text = "Hello in Welcome Screen",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}