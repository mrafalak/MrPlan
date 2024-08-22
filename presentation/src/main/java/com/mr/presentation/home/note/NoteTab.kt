package com.mr.presentation.home.note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mr.presentation.R
import com.mr.presentation.home.base.HomeNavigator

class NoteTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.nav_label_note)
            val icon = painterResource(R.drawable.ic_note_24)

            return remember {
                TabOptions(
                    index = 4u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        HomeNavigator(initialScreen = NoteListScreen())
    }
}