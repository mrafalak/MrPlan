package com.mr.domain.model

enum class DeepLinkHomeDirection(val path: String) {
    TODAY(path = "today"),
    TASK(path = "task"),
    NONE(path = ""),
}

fun String.toDeepLinkHomeDirection(): DeepLinkHomeDirection =
    DeepLinkHomeDirection.entries.firstOrNull { homeDirection ->
        homeDirection.path == this
    } ?: DeepLinkHomeDirection.NONE