package com.mr.presentation.ui

import androidx.compose.runtime.Stable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey

@Stable
abstract class AndroidScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey
}