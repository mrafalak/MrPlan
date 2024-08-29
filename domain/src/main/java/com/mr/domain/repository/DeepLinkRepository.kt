package com.mr.domain.repository

import android.net.Uri
import com.mr.domain.model.DeepLink
import kotlinx.coroutines.flow.SharedFlow

interface DeepLinkRepository {
    val pendingDeepLink: SharedFlow<DeepLink>
    suspend fun addDeepLink(deepLink: DeepLink)
}