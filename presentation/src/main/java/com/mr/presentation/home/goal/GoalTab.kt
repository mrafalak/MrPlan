package com.mr.presentation.home.goal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mr.domain.model.DeepLink
import com.mr.domain.model.DeepLinkGoalDirection
import com.mr.presentation.R
import com.mr.presentation.home.base.HomeNavigator
import com.mr.domain.model.HomeTabEnum
import com.mr.domain.navigation.UiConfig
import com.mr.presentation.home.base.AndroidScreenHome
import com.mr.presentation.home.base.LocalHomeNavigator
import com.mr.presentation.ui.LoadingScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

class GoalTab(
    private val deepLinkPath: String? = null
) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.nav_label_goal)
            val icon = painterResource(R.drawable.ic_goal_24)

            return remember {
                TabOptions(
                    index = HomeTabEnum.GOAL.index,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: GoalViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        HomeNavigator(initialScreen = state.initialScreen) { homeNavigator ->
            LaunchedEffect(viewModel.effect) {
                viewModel.effect.collectLatest {
                    when (it) {
                        GoalEffect.NavigateToGoalDetails -> {
                            println("DeepLink - GoalTab view model effect handle deep link")
                            homeNavigator.noDebounce.push(GoalDetailsScreen())
                        }
                    }
                }
            }

            LaunchedEffect(key1 = deepLinkPath) {
                deepLinkPath?.let {
                    viewModel.handleDeepLink(DeepLink.parse(deepLinkPath))
                }
            }
        }
    }
}