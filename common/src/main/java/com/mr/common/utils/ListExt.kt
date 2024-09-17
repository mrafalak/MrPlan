package com.mr.common.utils

fun createTabDeepLinkSubList(paths: List<String>): List<String> {
    return paths.subList(1, paths.size)
}