package com.mr.data.repository

import android.app.Activity.RESULT_OK
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mr.domain.repository.SessionRepository
import com.mr.domain.state.AuthState
import kotlinx.coroutines.delay
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

    override fun handleFirebaseLoginResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser
            if (user != null) {
                login(user)
            } else {
                auth.signOut()
            }
        } else {
            if (response == null) {
                loginCancelled()
            } else {
                loginError(response.error?.errorCode)
            }
        }
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

    override suspend fun logout() {
        FirebaseAuth.getInstance().signOut()
        _authState.value = AuthState.SignedOut
        delay(1000).run {
            setUserNotSigned()
        }
    }
}