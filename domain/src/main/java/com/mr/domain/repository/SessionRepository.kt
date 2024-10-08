package com.mr.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.mr.domain.state.AuthState
import kotlinx.coroutines.flow.StateFlow

interface SessionRepository {
    val authState: StateFlow<AuthState>

    fun login(user: FirebaseUser)
    fun setUserNotSigned()
    fun loginCancelled()
    fun loginError(errorCode: Int?)
}