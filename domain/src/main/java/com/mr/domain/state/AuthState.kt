package com.mr.domain.state

import com.google.firebase.auth.FirebaseUser

sealed class AuthState {
    data class SignedIn(val user: FirebaseUser) : AuthState()
    data object NotSigned : AuthState()
    data object Cancelled : AuthState()
    data class Error(val errorCode: Int?) : AuthState()
}