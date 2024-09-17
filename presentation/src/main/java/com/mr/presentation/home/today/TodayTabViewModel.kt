package com.mr.presentation.home.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mr.common.utils.createTabDeepLinkSubList
import com.mr.domain.model.DeepLink
import com.mr.domain.model.DeepLinkHomeDirection
import com.mr.domain.model.DeepLinkTodayDirection
import com.mr.domain.model.toDeepLinkTodayDirection
import com.mr.domain.repository.DeepLinkRepository
import com.mr.presentation.home.base.AndroidScreenHome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TodayTabState(
    val initialScreen: AndroidScreenHome = TodayScreen(),
)

@HiltViewModel
class TodayTabViewModel @Inject constructor(
    private val deepLinkRepository: DeepLinkRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(TodayTabState())
    val state = _state.asStateFlow()

    private val _effect = Channel<TodayTabEffect>()
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
            if (isTaskDeepLink(deepLink)) {
                val todayDirection = determineDeepLinkTodayDirection(deepLink)
                when (todayDirection) {
                    DeepLinkTodayDirection.TASK_CREATE -> {
                        _effect.send(TodayTabEffect.NavigateToTaskCreate)
                    }

                    DeepLinkTodayDirection.NONE -> Unit
                }
            }
        }
    }

    private fun isTaskDeepLink(deepLink: DeepLink): Boolean {
        return deepLink.subPaths.firstOrNull() == DeepLinkHomeDirection.TASK.path
    }

    private fun determineDeepLinkTodayDirection(deepLink: DeepLink): DeepLinkTodayDirection {
        val todaySubPaths = createTabDeepLinkSubList(deepLink.subPaths)
        return todaySubPaths.firstOrNull()?.toDeepLinkTodayDirection()
            ?: DeepLinkTodayDirection.NONE
    }
}

sealed class TodayTabEffect {
    data object NavigateToTaskCreate : TodayTabEffect()
}