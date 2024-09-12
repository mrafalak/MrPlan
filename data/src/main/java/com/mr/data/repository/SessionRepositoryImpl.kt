package com.mr.data.repository

import com.mr.domain.repository.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor() : SessionRepository {
    private val _isUserLogged = MutableStateFlow(true)
    override val isUserLogged: StateFlow<Boolean> = _isUserLogged.asStateFlow()
}