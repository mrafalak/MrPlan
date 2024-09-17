package com.mr.presentation.home.base

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.tab.Tab
import com.mr.domain.model.DeepLink
import com.mr.domain.model.DeepLinkHomeDirection
import com.mr.domain.repository.DeepLinkRepository
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.actions.MenuNavigationHandler
import com.mr.domain.model.toDeepLinkHomeDirection
import com.mr.presentation.home.task.TaskTab
import com.mr.presentation.home.today.TodayTab
import com.mr.presentation.ui.components.bars.TopBarConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val initialTab: Tab = TodayTab(),
    val bottomBarTabs: List<Tab> = listOf(
        TodayTab(),
        TaskTab(),
    ),
    val topBarState: TopBarState = TopBarState.None,
    val bottomBarVisible: Boolean = false,
    val drawerState: DrawerState = DrawerState(initialValue = DrawerValue.Closed),
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deepLinkRepository: DeepLinkRepository
) : ViewModel(), MenuNavigationHandler {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _effect = Channel<HomeEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch {
            deepLinkRepository.pendingDeepLink.collectLatest { deepLink ->
                handleDeepLink(deepLink)
            }
        }
    }

    fun handleDeepLink(deepLink: DeepLink) {
        viewModelScope.launch {
            val homeDirection = deepLink.subPaths.firstOrNull()?.toDeepLinkHomeDirection()
                ?: DeepLinkHomeDirection.NONE
            when (homeDirection) {
                DeepLinkHomeDirection.TODAY -> {
                    _effect.send(HomeEffect.NavigateToTodayTab(deepLink))
                }

                DeepLinkHomeDirection.TASK -> {
                    _effect.send(HomeEffect.NavigateToTaskTab(deepLink))
                }

                DeepLinkHomeDirection.NONE -> Unit
            }
        }
    }

    fun setTopBarState(state: TopBarState) {
        _state.update { it.copy(topBarState = state) }
    }

    fun setBottomBarVisible(visible: Boolean) {
        _state.update { it.copy(bottomBarVisible = visible) }
    }

    override fun onClickMenu() {
        viewModelScope.launch {
            if (state.value.drawerState.isClosed) {
                _effect.send(HomeEffect.OnMenuClick(showMenu = true))
            } else {
                _effect.send(HomeEffect.OnMenuClick(showMenu = false))
            }
        }
    }
}

sealed class TopBarState {
    data object None : TopBarState()
    data class Visible(
        val navigator: MrNavigator? = null,
        val config: TopBarConfig = TopBarConfig(),
    ) : TopBarState()
}

sealed class HomeEffect {
    data class OnMenuClick(val showMenu: Boolean = false) : HomeEffect()
    data class NavigateToTodayTab(val deepLink: DeepLink) : HomeEffect()
    data class NavigateToTaskTab(val deepLink: DeepLink) : HomeEffect()
}