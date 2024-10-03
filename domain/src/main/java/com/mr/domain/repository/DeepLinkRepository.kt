package com.mr.domain.repository

import com.mr.domain.model.deeplink.DeepLink
import kotlinx.coroutines.flow.SharedFlow

interface DeepLinkRepository {
    val pendingDeepLink: SharedFlow<DeepLink>
    suspend fun addDeepLink(deepLink: DeepLink)
}