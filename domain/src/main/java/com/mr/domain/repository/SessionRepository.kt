package com.mr.domain.repository

import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseUser
import com.mr.domain.state.AuthState
import kotlinx.coroutines.flow.StateFlow

interface SessionRepository {
    val authState: StateFlow<AuthState>

    fun login(user: FirebaseUser)
    fun handleFirebaseLoginResult(result: FirebaseAuthUIAuthenticationResult)
    fun setUserNotSigned()
    fun loginCancelled()
    fun loginError(errorCode: Int?)
    suspend fun logout()
}