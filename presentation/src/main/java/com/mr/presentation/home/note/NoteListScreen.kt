package com.mr.presentation.home.note

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mr.presentation.R
import com.mr.presentation.home.base.AndroidScreenHome
import com.mr.presentation.home.base.HomeViewModel
import com.mr.presentation.home.base.TopBarState
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.ui.components.bars.TopBarConfig

class NoteListScreen : AndroidScreenHome() {

    @Composable
    override fun SetTopBarState(homeViewModel: HomeViewModel, navigator: MrNavigator) {
        homeViewModel.setTopBarState(
            TopBarState.Visible(
                navigator = navigator,
                config = TopBarConfig(
                    titleResId = R.string.nav_note_list,
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

        Text(text = "Hello in Note List Screen", style = MaterialTheme.typography.titleLarge)
    }
}