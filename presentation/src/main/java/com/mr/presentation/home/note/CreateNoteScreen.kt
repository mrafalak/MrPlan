package com.mr.presentation.home.note

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
import com.mr.presentation.main.LocalActivity
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.actions.TopBarNavigationAction
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.ui.components.bars.TopBarConfig

class CreateNoteScreen : AndroidScreenHome() {

    @Composable
    override fun SetTopBarState(
        homeViewModel: HomeViewModel,
        navigator: MrNavigator,
        defaultNavBackScreen: AndroidScreen?
    ) {
        val activity = LocalActivity.current

        homeViewModel.setTopBarState(
            TopBarState.Visible(
                navigator = navigator,
                config = TopBarConfig(
                    titleResId = R.string.nav_note_create,
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
        defaultNavBackScreen = NoteListScreen()
        super.Content()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Hello in Create Note Screen", style = MaterialTheme.typography.titleLarge)
        }
    }
}