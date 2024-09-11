package com.mr.presentation.home.base

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.mr.presentation.main.LocalActivity
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.actions.navigateBack

@Stable
abstract class AndroidScreenHome : AndroidScreen() {

    @Composable
    override fun Content() {
        val activity = LocalActivity.current
        val navigator = LocalHomeNavigator.current
        val homeViewModel = LocalHomeViewModel.current

        BackHandler {
            navigateBack(activity, navigator, defaultNavBackScreen)
        }

        SetTopBarState(homeViewModel, navigator, defaultNavBackScreen)
        SetBottomBarVisibility(homeViewModel)
    }

    @Composable
    open fun SetTopBarState(
        homeViewModel: HomeViewModel,
        navigator: MrNavigator,
        defaultNavBackScreen: AndroidScreen?
    ) {
        homeViewModel.setTopBarState(TopBarState.None)
    }

    @Composable
    open fun SetBottomBarVisibility(homeViewModel: HomeViewModel) {
        homeViewModel.setBottomBarVisible(false)
    }
}