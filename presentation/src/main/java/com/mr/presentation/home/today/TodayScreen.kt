package com.mr.presentation.home.today

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mr.presentation.R
import com.mr.presentation.home.base.AndroidScreenHome
import com.mr.presentation.home.base.HomeViewModel
import com.mr.presentation.home.base.LocalHomeNavigator
import com.mr.presentation.home.base.TopBarState
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.actions.TopBarNavigationAction
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.ui.components.bars.TopBarConfig
import kotlinx.coroutines.flow.collectLatest

class TodayScreen : AndroidScreenHome() {

    @Composable
    override fun SetTopBarState(
        activity: Activity,
        homeViewModel: HomeViewModel,
        navigator: MrNavigator,
        defaultNavBackScreen: AndroidScreen?
    ) {
        homeViewModel.setTopBarState(
            TopBarState.Visible(
                navigator = navigator,
                config = TopBarConfig(
                    titleResId = R.string.nav_label_today,
                    navigationAction = TopBarNavigationAction.Menu(homeViewModel),
                )
            )
        )
    }

    @Composable
    override fun SetBottomBarVisibility(homeViewModel: HomeViewModel) {
        homeViewModel.setBottomBarVisible(true)
    }

    @Composable
    override fun Content() {
        super.Content()
        val viewModel: TodayViewModel = hiltViewModel()
        val navigator = LocalHomeNavigator.current
        val state by viewModel.state.collectAsState()

        LaunchedEffect(viewModel.effect) {
            viewModel.effect.collectLatest {
                when (it) {
                    TodayEffect.NavigateToTaskCreate -> TODO()
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Hello in Today Screen", style = MaterialTheme.typography.titleLarge)
        }
    }
}