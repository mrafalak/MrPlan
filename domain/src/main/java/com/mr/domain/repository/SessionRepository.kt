package com.mr.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface SessionRepository {
    val isUserLogged: StateFlow<Boolean>
}