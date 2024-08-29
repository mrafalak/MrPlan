package com.mr.domain.model

enum class DeepLinkHomeDirection(val path: String) {
    GOAL(path = "goal"),
    BOOK(path = "book"),
    TRAINING(path = "training"),
    NOTE(path = "note"),
    NONE(path = ""),
}

fun String.toDeepLinkHomeDirection(): DeepLinkHomeDirection =
    DeepLinkHomeDirection.entries.firstOrNull { homeDirection ->
        homeDirection.path == this
    } ?: DeepLinkHomeDirection.NONE