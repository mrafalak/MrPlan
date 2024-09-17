package com.mr.presentation.home.base

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.mr.domain.model.DeepLink
import com.mr.domain.navigation.UiConfig
import com.mr.presentation.home.task.TaskTab
import com.mr.presentation.home.today.TodayTab
import com.mr.presentation.ui.components.bars.BottomBar
import com.mr.presentation.ui.components.bars.TopBar
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeTabNavigator(
    tabs: List<Tab>,
    initialTab: Tab,
    bottomBarVisible: Boolean,
    topBarState: TopBarState,
    viewModel: HomeViewModel,
    deepLinkPath: String? = null
) {
    TabNavigator(tab = initialTab) {
        val tabNavigator = LocalTabNavigator.current
        val state by viewModel.state.collectAsState()

        LaunchedEffect(viewModel.effect) {
            viewModel.effect.collectLatest {
                when (it) {
                    is HomeEffect.OnMenuClick -> {
                        if (it.showMenu) {
                            state.drawerState.open()
                        } else {
                            state.drawerState.close()
                        }
                    }

                    is HomeEffect.NavigateToTodayTab -> {
                        tabNavigator.current = TodayTab(it.deepLink.fullPath)
                    }

                    is HomeEffect.NavigateToTaskTab -> {
                        tabNavigator.current = TaskTab(it.deepLink.fullPath)
                    }
                }
            }
        }

        LaunchedEffect(key1 = deepLinkPath) {
            deepLinkPath?.let {
                viewModel.handleDeepLink(DeepLink.parse(deepLinkPath))
            }
        }

        Scaffold(
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