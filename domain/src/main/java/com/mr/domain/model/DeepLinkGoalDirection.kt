package com.mr.domain.model

enum class DeepLinkGoalDirection(val path: String) {
    DETAILS(path = "details"),
    CREATE(path = "create"),
    NONE(path = ""),
}

fun String.toDeepLinkGoalDirection(): DeepLinkGoalDirection =
    DeepLinkGoalDirection.entries.firstOrNull { goalDirection ->
        goalDirection.path == this
    } ?: DeepLinkGoalDirection.NONE