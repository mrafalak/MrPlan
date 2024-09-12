package com.mr.presentation.navigation.actions

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.mr.presentation.R
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.ui.AndroidScreen

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
        private val activity: Activity,
        private val navigator: MrNavigator,
        private val defaultNavBackScreen: AndroidScreen? = null,
    ) : TopBarNavigationAction(
        R.string.nav_back,
        Icons.AutoMirrored.Filled.ArrowBack
    ) {
        override fun onClick() {
            navigateBack(activity, navigator, defaultNavBackScreen)
        }
    }
}