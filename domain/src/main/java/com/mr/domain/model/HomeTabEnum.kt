package com.mr.domain.model

enum class HomeTabEnum(val index: UShort, val direction: DeepLinkHomeDirection) {
    GOAL(index = 0u, direction = DeepLinkHomeDirection.GOAL),
    BOOK(index = 1u, direction = DeepLinkHomeDirection.BOOK),
    TRAINING(index = 2u, direction = DeepLinkHomeDirection.TRAINING),
    NOTE(index = 3u, direction = DeepLinkHomeDirection.NOTE),
}