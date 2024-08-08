package com.mr.presentation.home.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.ui.components.bars.TopBarConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

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
}

sealed class TopBarState {
    data object None : TopBarState()
    data class Visible(
        val navigator: MrNavigator? = null,
        val config: TopBarConfig = TopBarConfig(),
    ) : TopBarState()
}