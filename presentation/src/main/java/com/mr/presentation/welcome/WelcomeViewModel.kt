package com.mr.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mr.domain.repository.SessionRepository
import com.mr.domain.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _effect = Channel<WelcomeEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch {
            sessionRepository.authState.collect { authState ->
                when (authState) {
                    is AuthState.SignedIn -> _effect.send(WelcomeEffect.NavigateToHomeScreen)
                    is AuthState.Error -> _effect.send(WelcomeEffect.DisplayLoginError)
                    else -> Unit
                }
            }
        }
    }
}

sealed class WelcomeEffect {
    data object NavigateToHomeScreen : WelcomeEffect()
    data object DisplayLoginError : WelcomeEffect()
}