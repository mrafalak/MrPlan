package com.mr.domain.model

enum class DeepLinkMainDirection(val path: String) {
    HOME(path = "home"),
    SETTINGS(path = "settings"),
    WELCOME(path = "welcome"),
    NONE(path = ""),
}

fun String.toDeepLinkMainDirection(): DeepLinkMainDirection =
    DeepLinkMainDirection.entries.firstOrNull { mainDirection ->
        mainDirection.path == this
    } ?: DeepLinkMainDirection.NONE