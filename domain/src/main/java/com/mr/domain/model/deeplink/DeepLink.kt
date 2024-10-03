package com.mr.domain.model.deeplink

import android.net.Uri

data class DeepLink(
    val fullPath: String,
    val mainDirection: DeepLinkMainDirection,
    val subPaths: List<String>
) {
    companion object {
        fun parse(deepLinkPath: String): DeepLink {
            val uri = Uri.parse(deepLinkPath)
            val path = uri.path.orEmpty()
            val segments = path.trimStart('/').split("/")

            val mainDirection =
                segments.firstOrNull()?.toDeepLinkMainDirection() ?: DeepLinkMainDirection.NONE

            val subPaths = if (segments.size > 1) {
                segments.subList(1, segments.size)
            } else {
                emptyList()
            }

            return DeepLink(
                fullPath = path,
                mainDirection = mainDirection,
                subPaths = subPaths
            )
        }
    }
}
