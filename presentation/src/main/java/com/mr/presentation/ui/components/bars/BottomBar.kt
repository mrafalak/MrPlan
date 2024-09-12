package com.mr.presentation.ui.components.bars

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.mr.domain.navigation.UiConfig
import com.mr.presentation.R

@Composable
fun BottomBar(visible: Boolean, tabs: List<Tab>) {
    val animDuration = UiConfig.NAVIGATION_ANIM_DURATION

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(durationMillis = animDuration)
        ) + slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = animDuration)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = animDuration)
        ) + slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = animDuration)
        )
    ) {
        NavigationBar {
            tabs.forEach { tab ->
                TabNavigationItem(tab = tab)
            }
        }
    }
}

@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current.options.index == tab.options.index,
        onClick = { tabNavigator.current = tab },
        label = { Text(tab.options.title) },
        icon = {
            Icon(
                painter = tab.options.icon
                    ?: painterResource(id = R.drawable.ic_error_24),
                contentDescription = null
            )
        }
    )
}