package com.mr.presentation.welcome

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mr.presentation.R
import com.mr.presentation.components.MrButton
import com.mr.presentation.home.base.HomeScreen
import com.mr.presentation.main.LocalActivity
import com.mr.presentation.main.MainViewModel
import com.mr.presentation.navigation.providers.LocalMrNavigator
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.ui.theme.MrPlanTheme
import kotlinx.coroutines.flow.collectLatest

class WelcomeScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val navigator = LocalMrNavigator.current
        val context = LocalContext.current
        val activity = LocalActivity.current
        val viewModel: WelcomeViewModel = hiltViewModel()
        val mainViewModel: MainViewModel = hiltViewModel(activity)

        LaunchedEffect(viewModel.effect) {
            viewModel.effect.collectLatest { effect ->
                when (effect) {
                    WelcomeEffect.NavigateToHomeScreen -> {
                        navigator.noDebounce.push(HomeScreen())
                    }

                    WelcomeEffect.DisplayLoginError -> {
                        Toast.makeText(
                            context,
                            context.getText(R.string.error_login),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        WelcomeScreenContent(onLogin = { mainViewModel.loginWithGoogle() })
    }
}

@Composable
fun WelcomeScreenContent(onLogin: () -> Unit = {}) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Scaffold { innerPaddings ->
        Box(
            modifier = Modifier
                .padding(innerPaddings)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight / 3)
                    .padding(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.header_welcome_screen),
                    style = MaterialTheme.typography.displayMedium,
                )
                MrButton(
                    text = stringResource(R.string.button_login),
                ) {
                    onLogin()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreviewLight(modifier: Modifier = Modifier) {
    MrPlanTheme(darkTheme = false) {
        WelcomeScreenContent()
    }
}

@Preview
@Composable
fun WelcomeScreenPreviewDark(modifier: Modifier = Modifier) {
    MrPlanTheme(darkTheme = true) {
        WelcomeScreenContent()
    }
}