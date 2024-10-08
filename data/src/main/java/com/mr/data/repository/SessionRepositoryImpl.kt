package com.mr.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mr.domain.repository.SessionRepository
import com.mr.domain.state.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor() : SessionRepository {

    private val _authState = MutableStateFlow<AuthState>(AuthState.NotSigned)
    override val authState: StateFlow<AuthState> = _authState

    init {
        val user = FirebaseAuth.getInstance().currentUser
        _authState.value = if (user != null) AuthState.SignedIn(user) else AuthState.NotSigned
    }

    override fun login(user: FirebaseUser) {
        _authState.value = AuthState.SignedIn(user)
    }

    override fun setUserNotSigned() {
        _authState.value = AuthState.NotSigned
    }

    override fun loginCancelled() {
        _authState.value = AuthState.Cancelled
    }

    override fun loginError(errorCode: Int?) {
        _authState.value = AuthState.Error(errorCode)
    }
}