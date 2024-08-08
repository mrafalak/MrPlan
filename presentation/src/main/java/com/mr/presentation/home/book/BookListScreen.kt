package com.mr.presentation.home.book

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
import com.mr.presentation.ui.components.bars.TopBarConfig

class BookListScreen : AndroidScreenHome() {

    @Composable
    override fun SetTopBarState(homeViewModel: HomeViewModel, navigator: MrNavigator) {
        homeViewModel.setTopBarState(
            TopBarState.Visible(
                navigator = navigator,
                config = TopBarConfig(
                    titleResId = R.string.nav_book_list,
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

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Hello in Book List Screen", style = MaterialTheme.typography.titleLarge)
        }
    }
}