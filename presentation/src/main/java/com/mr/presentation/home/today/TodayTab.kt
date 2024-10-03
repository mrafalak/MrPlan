package com.mr.presentation.home.today

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
import com.mr.domain.model.deeplink.DeepLink
import com.mr.presentation.R
import com.mr.presentation.home.base.HomeNavigator
import com.mr.domain.navigation.HomeTabEnum
import kotlinx.coroutines.flow.collectLatest

class TodayTab(
    private val deepLinkPath: String? = null
) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.nav_label_today)
            val icon = painterResource(R.drawable.ic_today_24)

            return remember {
                TabOptions(
                    index = HomeTabEnum.TODAY.index,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: TodayTabViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        HomeNavigator(initialScreen = state.initialScreen) { homeNavigator ->
            LaunchedEffect(viewModel.effect) {
                viewModel.effect.collectLatest {
                    when (it) {
                        TodayTabEffect.NavigateToTaskCreate -> TODO()
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