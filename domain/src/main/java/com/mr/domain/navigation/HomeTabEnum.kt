package com.mr.domain.navigation

import com.mr.domain.model.deeplink.DeepLinkHomeDirection

enum class HomeTabEnum(val index: UShort, val direction: DeepLinkHomeDirection) {
    TODAY(index = 0u, direction = DeepLinkHomeDirection.TODAY),
    TASK(index = 1u, direction = DeepLinkHomeDirection.TASK),
}