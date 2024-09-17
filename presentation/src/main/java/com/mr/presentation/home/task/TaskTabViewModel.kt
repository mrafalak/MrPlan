package com.mr.presentation.home.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mr.common.utils.createTabDeepLinkSubList
import com.mr.domain.model.DeepLink
import com.mr.domain.model.DeepLinkTaskDirection
import com.mr.domain.model.DeepLinkHomeDirection
import com.mr.domain.model.toDeepLinkTaskDirection
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

data class TaskTabState(
    val initialScreen: AndroidScreenHome = TaskListScreen(),
)

@HiltViewModel
class TaskTabViewModel @Inject constructor(
    private val deepLinkRepository: DeepLinkRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(TaskTabState())
    val state = _state.asStateFlow()

    private val _effect = Channel<TaskTabEffect>()
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
                val taskDirection = determineDeepLinkTaskDirection(deepLink)
                when (taskDirection) {
                    DeepLinkTaskDirection.CREATE -> {
                        _effect.send(TaskTabEffect.NavigateToTaskCreate)
                    }

                    DeepLinkTaskDirection.NONE -> Unit
                }
            }
        }
    }

    private fun isTaskDeepLink(deepLink: DeepLink): Boolean {
        return deepLink.subPaths.firstOrNull() == DeepLinkHomeDirection.TASK.path
    }

    private fun determineDeepLinkTaskDirection(deepLink: DeepLink): DeepLinkTaskDirection {
        val taskSubPaths = createTabDeepLinkSubList(deepLink.subPaths)
        return taskSubPaths.firstOrNull()?.toDeepLinkTaskDirection() ?: DeepLinkTaskDirection.NONE
    }
}

sealed class TaskTabEffect {
    data object NavigateToTaskCreate : TaskTabEffect()
}