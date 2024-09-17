package com.mr.domain.model

enum class HomeTabEnum(val index: UShort, val direction: DeepLinkHomeDirection) {
    TODAY(index = 0u, direction = DeepLinkHomeDirection.TODAY),
    TASK(index = 1u, direction = DeepLinkHomeDirection.TASK),
}