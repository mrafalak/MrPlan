package com.mr.presentation.home.base

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.home.book.BookTab
import com.mr.presentation.home.goal.GoalTab
import com.mr.presentation.home.note.NoteTab
import com.mr.presentation.home.training.TrainingTab
import com.mr.presentation.main.LocalMrNavigator
import kotlinx.coroutines.flow.collectLatest

val LocalHomeViewModel = staticCompositionLocalOf<HomeViewModel> {
    error("No HomeViewModel provided")
}

class HomeScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        val navigator = LocalMrNavigator.current
        val scope = rememberCoroutineScope()

        val bottomBarVisible by viewModel.bottomBarVisible.observeAsState(false)
        val topBarState by viewModel.topBarState.observeAsState(TopBarState.None)

        val bottomBarTabs = listOf(
            GoalTab(),
            BookTab(),
            TrainingTab(),
            NoteTab(),
        )

        val tab = when {
            // TODO initialScreen
            else -> bottomBarTabs.first()
        }

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
                }
            }
        }

        CompositionLocalProvider(LocalHomeViewModel provides viewModel) {
            ModalNavigationDrawer(
                drawerContent = {
                    HomeNavDrawer(
                        state = state,
                        scope = scope,
                        navigator = navigator
                    )
                },
                drawerState = state.drawerState
            ) {
                HomeTabNavigator(
                    tabs = bottomBarTabs,
                    tab = tab,
                    bottomBarVisible = bottomBarVisible,
                    topBarState = topBarState
                )
            }
        }
    }
}