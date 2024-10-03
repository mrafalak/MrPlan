package com.mr.data.repository

import com.mr.domain.model.util.Result
import com.mr.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor() : SessionRepository {
    private val _isUserLogged = MutableStateFlow(false)
    override val isUserLogged: StateFlow<Boolean> = _isUserLogged.asStateFlow()

    override fun login(): Flow<Result<Boolean>> = flow {
        // TODO login
        val isSuccess = true

        if (isSuccess) {
            emit(Result.Success(true))
        } else {
            emit(Result.Exception(error = IOException()))
        }
    }
}