package com.mr.presentation.home.goal

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mr.presentation.R
import com.mr.presentation.home.base.AndroidScreenHome
import com.mr.presentation.home.base.HomeViewModel
import com.mr.presentation.home.base.LocalHomeNavigator
import com.mr.presentation.home.base.TopBarState
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.actions.TopBarAction
import com.mr.presentation.navigation.actions.TopBarNavigationAction
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.ui.components.bars.TopBarConfig
import kotlinx.coroutines.flow.collectLatest

class GoalListScreen : AndroidScreenHome() {

    @Composable
    override fun SetTopBarState(
        activity: Activity,
        homeViewModel: HomeViewModel,
        navigator: MrNavigator,
        defaultNavBackScreen: AndroidScreen?
    ) {
        val viewModel: GoalListViewModel = viewModel()

        homeViewModel.setTopBarState(
            TopBarState.Visible(
                navigator = navigator,
                config = TopBarConfig(
                    titleResId = R.string.nav_goal_list,
                    navigationAction = TopBarNavigationAction.Menu(homeViewModel),
                    actions = listOf(
                        TopBarAction.Add(viewModel, R.string.ic_goal_add)
                    )
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
        val navigator = LocalHomeNavigator.current
        val viewModel: GoalListViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(viewModel.effect) {
            viewModel.effect.collectLatest {
                when (it) {
                    GoalListEffect.NavigateToCreateGoal -> {
                        navigator.push(CreateGoalScreen())
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Hello in Goal List Screen", style = MaterialTheme.typography.titleLarge)
            BasicTextField(
                value = state.testTextField,
                onValueChange = {
                    viewModel.updateTextField(it)
                }
            )
        }
    }
}