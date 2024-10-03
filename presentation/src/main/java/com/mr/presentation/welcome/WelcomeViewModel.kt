package com.mr.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mr.domain.model.util.Result
import com.mr.domain.repository.SessionRepository
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

    fun login() {
        viewModelScope.launch {
            sessionRepository.login().collect { result ->
                when (result) {
                    is Result.Success -> _effect.send(WelcomeEffect.NavigateToHomeScreen)
                    is Result.Exception -> _effect.send(WelcomeEffect.DisplayLoginError)
                }
            }
        }
    }
}

sealed class WelcomeEffect {
    data object NavigateToHomeScreen : WelcomeEffect()
    data object DisplayLoginError : WelcomeEffect()
}