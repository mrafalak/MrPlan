package com.mr.domain.model

enum class DeepLinkTodayDirection(val path: String) {
    TASK_CREATE(path = "task_create"),
    NONE(path = ""),
}

fun String.toDeepLinkTodayDirection(): DeepLinkTodayDirection =
    DeepLinkTodayDirection.entries.firstOrNull { todayDirection ->
        todayDirection.path == this
    } ?: DeepLinkTodayDirection.NONE