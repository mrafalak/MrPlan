package com.mr.presentation.home.base

import android.content.Intent
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.tab.Tab
import com.mr.domain.model.DeepLink
import com.mr.domain.model.DeepLinkHomeDirection
import com.mr.domain.repository.DeepLinkRepository
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.actions.MenuNavigationHandler
import com.mr.domain.model.DeepLinkMainDirection
import com.mr.presentation.home.book.BookTab
import com.mr.presentation.home.goal.GoalTab
import com.mr.presentation.home.note.NoteTab
import com.mr.presentation.home.training.TrainingTab
import com.mr.presentation.main.MainEffect
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.ui.components.bars.TopBarConfig
import com.mr.presentation.welcome.WelcomeScreen
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

    private val _topBarState = MutableLiveData<TopBarState>(TopBarState.None)
    val topBarState: LiveData<TopBarState> = _topBarState

    private val _bottomBarVisible = MutableLiveData(false)
    val bottomBarVisible: LiveData<Boolean> = _bottomBarVisible

    init {
        println("DeepLink - HomeViewModel init")
        viewModelScope.launch {
            deepLinkRepository.pendingDeepLink.collectLatest { deepLink ->
                handleDeepLink(deepLink)
            }
        }
    }

    private fun handleDeepLink(deepLink: DeepLink) {
        viewModelScope.launch {
//            println("DeepLink - HomeViewModel handleDeepLink")
            when (deepLink.subPaths.first()) {
                DeepLinkHomeDirection.GOAL.path -> {
                    _effect.send(HomeEffect.NavigateToGoalTab(deepLink))
                }

                else -> Unit
            }
        }
    }

    fun setTopBarState(state: TopBarState) {
        _topBarState.value = state
    }

    fun setBottomBarVisible(visible: Boolean) {
        _bottomBarVisible.value = visible
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

    fun determineInitialTab(deepLinkPath: String?): Tab {
        val defaultTab = state.value.bottomBarTabs.last()

        return if (deepLinkPath == null) {
            defaultTab
        } else {
            val deepLink = DeepLink.parse(deepLinkPath)

            when (deepLink.subPaths.first()) {
                DeepLinkHomeDirection.GOAL.path -> {
                    println("DeepLink - HomeScreen handle deep link")
                    GoalTab(deepLink.fullPath)
                }

                DeepLinkHomeDirection.BOOK.path -> {
                    BookTab()
                }

                DeepLinkHomeDirection.TRAINING.path -> {
                    TrainingTab()
                }

                DeepLinkHomeDirection.NOTE.path -> {
                    NoteTab()
                }

                else -> defaultTab
            }
        }

//        setInitialTab(tab = initialTab)
    }

    private fun setInitialTab(tab: Tab) {
        _state.update { it.copy(initialTab = tab) }
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
}