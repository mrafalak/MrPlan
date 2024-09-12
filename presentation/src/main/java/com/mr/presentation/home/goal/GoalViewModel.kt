package com.mr.presentation.home.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mr.domain.model.DeepLink
import com.mr.domain.model.DeepLinkGoalDirection
import com.mr.domain.model.DeepLinkHomeDirection
import com.mr.domain.model.toDeepLinkGoalDirection
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

data class GoalTabState(
    val initialScreen: AndroidScreenHome = GoalListScreen(),
)

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val deepLinkRepository: DeepLinkRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GoalTabState())
    val state = _state.asStateFlow()

    private val _effect = Channel<GoalEffect>()
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
            if (isGoalDeepLink(deepLink)) {
                val goalDirection = determineDeepLinkGoalDirection(deepLink)
                when (goalDirection) {
                    DeepLinkGoalDirection.DETAILS -> {
                        _effect.send(GoalEffect.NavigateToGoalDetails)
                    }

                    DeepLinkGoalDirection.CREATE -> {
                        _effect.send(GoalEffect.NavigateToGoalCreate)
                    }

                    DeepLinkGoalDirection.NONE -> Unit
                }
            }
        }
    }

    private fun isGoalDeepLink(deepLink: DeepLink): Boolean {
        return deepLink.subPaths.firstOrNull() == DeepLinkHomeDirection.GOAL.path
    }

    private fun createGoalSubPathList(deepLink: DeepLink): List<String> {
        return deepLink.subPaths.subList(1, deepLink.subPaths.size)
    }

    private fun determineDeepLinkGoalDirection(deepLink: DeepLink): DeepLinkGoalDirection {
        val goalSubPaths = createGoalSubPathList(deepLink)
        return goalSubPaths.firstOrNull()?.toDeepLinkGoalDirection() ?: DeepLinkGoalDirection.NONE
    }
}

sealed class GoalEffect {
    data object NavigateToGoalDetails : GoalEffect()
    data object NavigateToGoalCreate : GoalEffect()
}