package com.mr.data.repository

import com.mr.domain.model.deeplink.DeepLink
import com.mr.domain.repository.DeepLinkRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeepLinkRepositoryImpl @Inject constructor() : DeepLinkRepository {

    private val _pendingDeepLink = MutableSharedFlow<DeepLink>(extraBufferCapacity = 64)
    override val pendingDeepLink: SharedFlow<DeepLink> = _pendingDeepLink

    override suspend fun addDeepLink(deepLink: DeepLink) {
        _pendingDeepLink.emit(deepLink)
    }
}