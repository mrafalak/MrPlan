package com.mr.presentation.home.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mr.presentation.navigation.actions.AddActionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalListViewModel @Inject constructor() : ViewModel(), AddActionHandler {

    private val _effect = Channel<GoalListEffect>()
    val effect = _effect.receiveAsFlow()

    override fun onAdd() {
        viewModelScope.launch {
            _effect.send(GoalListEffect.NavigateToCreateGoal)
        }
    }
}

sealed class GoalListEffect {
    data object NavigateToCreateGoal : GoalListEffect()
}