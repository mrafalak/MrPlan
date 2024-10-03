package com.mr.domain.repository

import com.mr.domain.model.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SessionRepository {
    val isUserLogged: StateFlow<Boolean>

    fun login(): Flow<Result<Boolean>>
}