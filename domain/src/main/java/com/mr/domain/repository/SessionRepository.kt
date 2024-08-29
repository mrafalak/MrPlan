package com.mr.domain.repository

import android.net.Uri
import com.mr.domain.model.DeepLink
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface SessionRepository {
    val isUserLogged: StateFlow<Boolean>
}