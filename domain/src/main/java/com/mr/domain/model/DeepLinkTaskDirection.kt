package com.mr.domain.model

enum class DeepLinkTaskDirection(val path: String) {
    CREATE(path = "create"),
    NONE(path = ""),
}

fun String.toDeepLinkTaskDirection(): DeepLinkTaskDirection =
    DeepLinkTaskDirection.entries.firstOrNull { taskDirection ->
        taskDirection.path == this
    } ?: DeepLinkTaskDirection.NONE