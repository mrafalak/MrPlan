package com.mr.presentation.home.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.home.book.BookTab
import com.mr.presentation.home.goal.GoalTab
import com.mr.presentation.home.note.NoteTab
import com.mr.presentation.home.training.TrainingTab

val LocalHomeViewModel = staticCompositionLocalOf<HomeViewModel> {
    error("No HomeViewModel provided")
}

class HomeScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = hiltViewModel()
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

        CompositionLocalProvider(LocalHomeViewModel provides viewModel) {
            HomeTabNavigator(
                tabs = bottomBarTabs,
                tab = tab,
                bottomBarVisible = bottomBarVisible,
                topBarState = topBarState
            )
        }
    }
}