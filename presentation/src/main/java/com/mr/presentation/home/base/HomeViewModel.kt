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
import com.mr.presentation.home.book.BookTab
import com.mr.presentation.home.goal.GoalTab
import com.mr.presentation.home.note.NoteTab
import com.mr.presentation.home.training.TrainingTab
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
    val initialTab: Tab = GoalTab(),
    val bottomBarTabs: List<Tab> = listOf(
        GoalTab(),
        BookTab(),
        TrainingTab(),
        NoteTab(),
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
                DeepLinkHomeDirection.GOAL -> {
                    _effect.send(HomeEffect.NavigateToGoalTab(deepLink))
                }

                DeepLinkHomeDirection.BOOK -> {
                    _effect.send(HomeEffect.NavigateToBookTab(deepLink))
                }

                DeepLinkHomeDirection.TRAINING -> {
                    _effect.send(HomeEffect.NavigateToTrainingTab(deepLink))
                }

                DeepLinkHomeDirection.NOTE -> {
                    _effect.send(HomeEffect.NavigateToNoteTab(deepLink))
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
    data class NavigateToGoalTab(val deepLink: DeepLink) : HomeEffect()
    data class NavigateToBookTab(val deepLink: DeepLink) : HomeEffect()
    data class NavigateToTrainingTab(val deepLink: DeepLink) : HomeEffect()
    data class NavigateToNoteTab(val deepLink: DeepLink) : HomeEffect()
}