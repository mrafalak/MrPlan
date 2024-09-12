package com.mr.presentation.ui.animations

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import cafe.adriel.voyager.transitions.SlideTransition
import com.mr.domain.navigation.UiConfig

@OptIn(ExperimentalVoyagerApi::class)
@Composable
fun DefaultSlideTransition(navigator: Navigator) {
    SlideTransition(
        navigator = navigator,
        disposeScreenAfterTransitionEnd = true,
        animationSpec = tween(durationMillis = UiConfig.NAVIGATION_ANIM_DURATION)
    )
}

@OptIn(ExperimentalVoyagerApi::class)
@Composable
fun DefaultFadeTransition(navigator: Navigator) {
    FadeTransition(
        navigator = navigator,
        disposeScreenAfterTransitionEnd = true,
        animationSpec = tween(durationMillis = UiConfig.NAVIGATION_ANIM_DURATION)
    )
}