package com.mr.presentation.navigation.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector
import com.mr.presentation.R
import com.mr.presentation.navigation.MrNavigator

sealed class TopBarAction(
    val titleResId: Int,
    val icon: ImageVector
) {
    abstract fun onClick()

    class Add(
        private val handler: AddActionHandler,
        titleResId: Int
    ) : TopBarAction(
        titleResId,
        Icons.Default.Add
    ) {
        override fun onClick() {
            handler.onAdd()
        }
    }

    class NavigationBack(
        private val navigator: MrNavigator
    ) : TopBarAction(
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