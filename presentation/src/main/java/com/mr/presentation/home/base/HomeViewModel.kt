package com.mr.presentation.home.base

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.actions.MenuNavigationHandler
import com.mr.presentation.ui.components.bars.TopBarConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val drawerState: DrawerState = DrawerState(initialValue = DrawerValue.Closed),
)

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel(), MenuNavigationHandler {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _effect = Channel<HomeEffect>()
    val effect = _effect.receiveAsFlow()

    private val _topBarState = MutableLiveData<TopBarState>(TopBarState.None)
    val topBarState: LiveData<TopBarState> = _topBarState

    private val _bottomBarVisible = MutableLiveData(false)
    val bottomBarVisible: LiveData<Boolean> = _bottomBarVisible

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
}