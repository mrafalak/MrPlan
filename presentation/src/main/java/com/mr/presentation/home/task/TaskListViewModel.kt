package com.mr.presentation.home.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mr.presentation.navigation.actions.AddActionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class TaskListState(
    val date: Date = Date()
)

@HiltViewModel
class TaskListViewModel @Inject constructor() : ViewModel(), AddActionHandler {

    private val _state = MutableStateFlow(TaskListState())
    val state = _state.asStateFlow()

    private val _effect = Channel<TaskListEffect>()
    val effect = _effect.receiveAsFlow()

    override fun onAdd() {
        viewModelScope.launch {
            _effect.send(TaskListEffect.NavigateToTaskCreate)
        }
    }
}

sealed class TaskListEffect {
    data object NavigateToTaskCreate : TaskListEffect()
}