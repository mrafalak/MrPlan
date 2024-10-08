package com.mr.presentation.navigation.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector

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

    class Logout(
        private val handler: LogoutActionHandler,
        titleResId: Int
    ) : TopBarAction(
        titleResId,
        Icons.AutoMirrored.Filled.ExitToApp
    ) {
        override fun onClick() {
            handler.onLogout()
        }
    }
}