package com.mr.presentation.navigation.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.mr.presentation.R
import com.mr.presentation.navigation.MrNavigator

sealed class TopBarNavigationAction(
    val titleResId: Int,
    val icon: ImageVector
) {
    abstract fun onClick()

    class Menu(
        private val handler: MenuNavigationHandler,
    ) : TopBarNavigationAction(
        R.string.nav_menu,
        Icons.Filled.Menu
    ) {
        override fun onClick() {
            handler.onClickMenu()
        }
    }

    class NavigationBack(
        private val navigator: MrNavigator
    ) : TopBarNavigationAction(
        R.string.nav_back,
        Icons.AutoMirrored.Filled.ArrowBack
    ) {
        override fun onClick() {
            if (navigator.canPop) {
                navigator.pop()
            }
        }
    }
}