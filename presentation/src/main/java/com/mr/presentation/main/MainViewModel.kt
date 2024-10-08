package com.mr.presentation.main

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.mr.domain.model.deeplink.DeepLink
import com.mr.domain.repository.DeepLinkRepository
import com.mr.domain.model.deeplink.DeepLinkMainDirection
import com.mr.domain.repository.SessionRepository
import com.mr.domain.state.AuthState
import com.mr.presentation.BuildConfig
import com.mr.presentation.home.base.HomeScreen
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.welcome.WelcomeScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainActivityState(
    val initialScreen: AndroidScreen = WelcomeScreen(),
    val authState: AuthState,
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deepLinkRepository: DeepLinkRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        MainActivityState(
            authState = sessionRepository.authState.value
        )
    )
    val state = _state.asStateFlow()

    private val _effect = Channel<MainEffect>()
    val effect = _effect.receiveAsFlow()

    private val _loginIntent = MutableSharedFlow<Intent>()
    val loginIntent = _loginIntent.asSharedFlow()

    init {
        viewModelScope.launch {
            deepLinkRepository.pendingDeepLink.collectLatest { deepLink ->
                handleDeepLink(deepLink)
            }
        }
    }

    fun determineInitialScreen(intent: Intent) {
        val isIntentWithDeepLink = intent.data != null
        val authState = state.value.authState

        val screen = when {
            isIntentWithDeepLink -> WelcomeScreen()
            BuildConfig.DEBUG -> HomeScreen()
            authState is AuthState.SignedIn -> {
                HomeScreen()
            }

            else -> WelcomeScreen()
        }

        setInitialScreen(screen)
    }

    private fun setInitialScreen(screen: AndroidScreen) {
        _state.update { it.copy(initialScreen = screen) }
    }

    fun enqueueDeepLink(deepLinkPath: String) {
        viewModelScope.launch {
            deepLinkRepository.addDeepLink(DeepLink.parse(deepLinkPath))
        }
    }

    private fun handleDeepLink(deepLink: DeepLink) {
        viewModelScope.launch {
            when (deepLink.mainDirection) {
                DeepLinkMainDirection.HOME -> {
                    _effect.send(MainEffect.NavigateToHomeScreen(deepLink))
                }

                else -> Unit
            }
        }
    }

    fun loginWithGoogle() {
        viewModelScope.launch {
            val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
            )
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()

            _loginIntent.emit(signInIntent)
        }
    }

    fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        sessionRepository.handleFirebaseLoginResult(result)
    }
}

sealed class MainEffect {
    data class NavigateToHomeScreen(val deepLink: DeepLink) : MainEffect()
}