package com.mr.presentation.home.goal

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mr.presentation.R
import com.mr.presentation.home.base.AndroidScreenHome
import com.mr.presentation.home.base.HomeViewModel
import com.mr.presentation.home.base.TopBarState
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.actions.TopBarNavigationAction
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.ui.components.bars.TopBarConfig

class CreateGoalScreen : AndroidScreenHome() {

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
                    titleResId = R.string.nav_goal_create,
                    navigationAction = TopBarNavigationAction.NavigationBack(
                        activity = activity,
                        navigator = navigator,
                        defaultNavBackScreen = defaultNavBackScreen
                    )
                ),
            )
        )
    }

    @Composable
    override fun Content() {
        defaultNavBackScreen = GoalListScreen()
        super.Content()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Hello in Create Goal Screen", style = MaterialTheme.typography.titleLarge)
        }
    }
}