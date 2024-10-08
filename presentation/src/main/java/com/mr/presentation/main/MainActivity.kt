package com.mr.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.mr.presentation.ui.theme.MrPlanTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

val LocalActivity = staticCompositionLocalOf<ComponentActivity> {
    error("CompositionLocal Activity not present")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        mainViewModel.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        mainViewModel.determineInitialScreen(intent)
        setContent()
        handleDeepLinkIntent(intent)
        handleLoginIntent()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeepLinkIntent(intent)
    }

    private fun handleDeepLinkIntent(intent: Intent) {
        intent.data?.path?.let { path ->
            mainViewModel.enqueueDeepLink(path)
        }
    }

    private fun handleLoginIntent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.loginIntent.collect { signInIntent ->
                    signInLauncher.launch(signInIntent)
                }
            }
        }
    }

    private fun setContent() {
        setContent {
            val state by mainViewModel.state.collectAsState()

            CompositionLocalProvider(LocalActivity provides this) {
                MrPlanTheme {
                    MainNavigator(
                        initialScreen = state.initialScreen,
                        viewModel = mainViewModel
                    )
                }
            }
        }
    }
}