package com.mr.presentation.home.training

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mr.presentation.R
import com.mr.presentation.home.base.AndroidScreenHome
import com.mr.presentation.home.base.HomeViewModel
import com.mr.presentation.home.base.TopBarState
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.actions.TopBarNavigationAction
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.ui.components.bars.TopBarConfig

class TrainingListScreen : AndroidScreenHome() {

    @Composable
    override fun SetTopBarState(
        homeViewModel: HomeViewModel,
        navigator: MrNavigator,
        defaultNavBackScreen: AndroidScreen?
    ) {
        homeViewModel.setTopBarState(
            TopBarState.Visible(
                navigator = navigator,
                config = TopBarConfig(
                    titleResId = R.string.nav_training_list,
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

        Text(text = "Hello in Training List Screen", style = MaterialTheme.typography.titleLarge)
    }
}