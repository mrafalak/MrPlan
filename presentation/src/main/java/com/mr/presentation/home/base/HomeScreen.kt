package com.mr.presentation.home.base

import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.navigation.providers.LocalMrNavigator

val LocalHomeViewModel = staticCompositionLocalOf<HomeViewModel> {
    error("No HomeViewModel provided")
}

class HomeScreen(
    private val deepLinkPath: String? = null
) : AndroidScreen() {

    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        val navigator = LocalMrNavigator.current
        val scope = rememberCoroutineScope()

        val bottomBarVisible by viewModel.bottomBarVisible.observeAsState(false)
        val topBarState by viewModel.topBarState.observeAsState(TopBarState.None)

        CompositionLocalProvider(LocalHomeViewModel provides viewModel) {
            ModalNavigationDrawer(
                drawerContent = {
                    HomeNavDrawer(
                        state = state.drawerState,
                        scope = scope,
                        navigator = navigator
                    )
                },
                drawerState = state.drawerState
            ) {
                HomeTabNavigator(
                    tabs = state.bottomBarTabs,
                    initialTab = state.initialTab,
                    bottomBarVisible = bottomBarVisible,
                    topBarState = topBarState,
                    viewModel = viewModel,
                    deepLinkPath = deepLinkPath
                )
            }
        }
    }
}