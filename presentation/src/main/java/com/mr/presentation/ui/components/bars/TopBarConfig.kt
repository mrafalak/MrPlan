package com.mr.presentation.ui.components.bars

import com.mr.presentation.navigation.actions.TopBarAction
import com.mr.presentation.navigation.actions.TopBarNavigationAction

data class TopBarConfig(
    val titleResId: Int? = null,
    val navigationAction: TopBarNavigationAction? = null,
    val actions: List<TopBarAction> = emptyList(),
)