package com.mr.presentation.welcome

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor() : ViewModel() {

    private val _effect = Channel<WelcomeEffect>()
    val effect = _effect.receiveAsFlow()
}

sealed class WelcomeEffect {
    data object NavigateToHomeScreen : WelcomeEffect()
}