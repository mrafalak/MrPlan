package com.mr.presentation.home.goal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mr.presentation.R
import com.mr.presentation.home.base.HomeNavigator

class GoalTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.nav_label_goal)
            val icon = painterResource(R.drawable.ic_goal_24)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        HomeNavigator(initialScreen = GoalListScreen())
    }
}