package com.mr.presentation.home.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.navigation.MrNavigator

@Stable
abstract class AndroidScreenHome : AndroidScreen() {

    @Composable
    override fun Content() {
        val navigator = LocalHomeNavigator.current
        val homeViewModel = LocalHomeViewModel.current

        SetTopBarState(homeViewModel, navigator)
        SetBottomBarVisibility(homeViewModel)
    }

    @Composable
    open fun SetTopBarState(homeViewModel: HomeViewModel, navigator: MrNavigator) {
        homeViewModel.setTopBarState(TopBarState.None)
    }

    @Composable
    open fun SetBottomBarVisibility(homeViewModel: HomeViewModel) {
        homeViewModel.setBottomBarVisible(false)
    }
}