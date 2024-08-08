package com.mr.presentation.ui.components.bars

import com.mr.presentation.navigation.actions.TopBarAction

data class TopBarConfig(
    val titleResId: Int? = null,
    val isNavigationEnabled: Boolean = true,
    val actions: List<TopBarAction> = emptyList(),
)