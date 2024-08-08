package com.mr.presentation.ui.components.bars

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import com.mr.domain.navigation.UiConfig
import com.mr.presentation.home.base.TopBarState
import com.mr.presentation.navigation.MrNavigator

@Composable
fun TopBar(topBarState: TopBarState) {
    val animDuration = UiConfig.NAVIGATION_ANIM_DURATION

    AnimatedVisibility(
        visible = topBarState !is TopBarState.None,
        enter = fadeIn(
            animationSpec = tween(durationMillis = animDuration)
        ) + slideInVertically(
            animationSpec = tween(durationMillis = animDuration)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = animDuration)
        ) + slideOutVertically(
            animationSpec = tween(durationMillis = animDuration)
        )
    ) {
        val navigator: MrNavigator? = when (topBarState) {
            is TopBarState.None -> null
            is TopBarState.Visible -> topBarState.navigator
        }

        val config: TopBarConfig = when (topBarState) {
            is TopBarState.None -> TopBarConfig()
            is TopBarState.Visible -> topBarState.config
        }

        MrTopBar(
            navigator = navigator,
            config = config
        )
    }
}