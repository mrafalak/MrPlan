package com.mr.presentation.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mr.domain.repository.SessionRepository
import com.mr.domain.state.AuthState
import com.mr.presentation.BuildConfig
import com.mr.presentation.home.base.TopBarState
import com.mr.presentation.navigation.actions.LogoutActionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileState(
    val topBarState: TopBarState = TopBarState.None,
    val userPhoto: Uri? = null,
    val userDisplayName: String? = null,
    val userEmail: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel(), LogoutActionHandler {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _effect = Channel<ProfileEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch {
            sessionRepository.authState.collect { authState ->
                if (BuildConfig.DEBUG) {
                    _state.update { DebugProfileState }
                } else {
                    when (authState) {
                        is AuthState.SignedIn -> {
                            _state.update {
                                it.copy(
                                    userPhoto = authState.user.photoUrl,
                                    userDisplayName = authState.user.displayName,
                                    userEmail = authState.user.email
                                )
                            }
                        }

                        AuthState.NotSigned -> {
                            _effect.send(ProfileEffect.UserNotFound)
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    fun setTopBarState(state: TopBarState) {
        _state.update { it.copy(topBarState = state) }
    }

    override fun onLogout() {
        viewModelScope.launch {
            sessionRepository.logout()
        }
    }
}

sealed class ProfileEffect {
    data object UserNotFound : ProfileEffect()
}

val DebugProfileState = ProfileState(
    userPhoto = Uri.parse("https://e7.pngegg.com/pngimages/915/108/png-clipart-illustrator-email-flat-avatar-trademark-logo.png"),
    userDisplayName = "Test Display Name",
    userEmail = "profile.test@email.com"
)