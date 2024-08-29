package com.mr.presentation.home.note

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

class NoteListScreen : AndroidScreenHome() {

    @Composable
    override fun SetTopBarState(
        homeViewModel: HomeViewModel,
        navigator: MrNavigator,
        defaultNavBackScreen: AndroidScreen?
    ) {
        val viewModel: NoteListViewModel = viewModel()

        homeViewModel.setTopBarState(
            TopBarState.Visible(
                navigator = navigator,
                config = TopBarConfig(
                    titleResId = R.string.nav_note_list,
                    navigationAction = TopBarNavigationAction.Menu(homeViewModel),
                    actions = listOf(
                        TopBarAction.Add(viewModel, R.string.ic_note_add)
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
        val viewModel: NoteListViewModel = hiltViewModel()

        LaunchedEffect(viewModel.effect) {
            viewModel.effect.collectLatest {
                when (it) {
                    NoteListEffect.NavigateToCreateNote -> {
                        println("DeepLink - NoteListScreen handle deep link")
                        navigator.push(CreateNoteScreen())
                    }
                }
            }
        }

        Text(text = "Hello in Note List Screen", style = MaterialTheme.typography.titleLarge)
    }
}