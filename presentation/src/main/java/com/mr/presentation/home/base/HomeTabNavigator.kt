package com.mr.presentation.home.base

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.mr.domain.navigation.UiConfig
import com.mr.presentation.ui.components.bars.BottomBar
import com.mr.presentation.ui.components.bars.TopBar

@Composable
fun HomeTabNavigator(
    tabs: List<Tab>,
    tab: Tab,
    bottomBarVisible: Boolean,
    topBarState: TopBarState
) {
    TabNavigator(tab = tab) {
        Scaffold(
            modifier = Modifier.systemBarsPadding(),
            topBar = {
                TopBar(topBarState)
            },
            bottomBar = {
                BottomBar(bottomBarVisible, tabs)
            }
        ) { innerPadding ->
            val animatedTopPadding by animateDpAsState(
                targetValue = if (topBarState is TopBarState.None) 0.dp else innerPadding.calculateTopPadding(),
                animationSpec = tween(UiConfig.NAVIGATION_ANIM_DURATION), label = ""
            )

            Box(
                modifier = Modifier.padding(top = animatedTopPadding)
            ) {
                CurrentTab()
            }
        }
    }
}