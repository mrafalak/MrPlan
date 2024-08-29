package com.mr.presentation.home.goal

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mr.domain.model.DeepLink
import com.mr.domain.model.DeepLinkGoalDirection
import com.mr.domain.model.DeepLinkHomeDirection
import com.mr.domain.repository.DeepLinkRepository
import com.mr.presentation.BuildConfig
import com.mr.presentation.home.base.AndroidScreenHome
import com.mr.presentation.main.MainActivityState
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.ui.LoadingScreen
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
        println("DeepLink - GoalViewModel init")
        viewModelScope.launch {
            deepLinkRepository.pendingDeepLink.collectLatest { deepLink ->
                handleDeepLink(deepLink)
            }
        }
    }

    fun handleDeepLink(deepLink: DeepLink) {
        viewModelScope.launch {
            println("DeepLink - GoalViewModel handle deep link")
            if (isGoalDeepLink(deepLink)) {
                val goalDirection = determineDeepLinkGoalDirection(deepLink)
                when (goalDirection) {
                    DeepLinkGoalDirection.DETAILS -> {
                        _effect.send(GoalEffect.NavigateToGoalDetails)
                    }

                    DeepLinkGoalDirection.CREATE -> TODO()
                    DeepLinkGoalDirection.NONE -> TODO()
                }
            }
        }
    }

    private fun isGoalDeepLink(deepLink: DeepLink): Boolean {
        return deepLink.subPaths.first() == DeepLinkHomeDirection.GOAL.path
    }

    private fun createGoalSubPathList(deepLink: DeepLink): List<String> {
        return deepLink.subPaths.subList(1, deepLink.subPaths.size)
    }

    private fun determineDeepLinkGoalDirection(deepLink: DeepLink): DeepLinkGoalDirection {
        val goalSubPaths = createGoalSubPathList(deepLink)

        return when {
            goalSubPaths.first() == DeepLinkGoalDirection.DETAILS.path -> DeepLinkGoalDirection.DETAILS
            goalSubPaths.first() == DeepLinkGoalDirection.CREATE.path -> DeepLinkGoalDirection.CREATE
            else -> DeepLinkGoalDirection.NONE
        }
    }
}

sealed class GoalEffect {
    data object NavigateToGoalDetails : GoalEffect()
}