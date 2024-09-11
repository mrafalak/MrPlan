package com.mr.presentation.home.goal

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mr.presentation.navigation.actions.AddActionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GoalListState(
    val testTextField: TextFieldValue = TextFieldValue(""),
)

@HiltViewModel
class GoalListViewModel @Inject constructor() : ViewModel(), AddActionHandler {

    private val _state = MutableStateFlow(GoalListState())
    val state = _state.asStateFlow()

    private val _effect = Channel<GoalListEffect>()
    val effect = _effect.receiveAsFlow()

    override fun onAdd() {
        viewModelScope.launch {
            _effect.send(GoalListEffect.NavigateToCreateGoal)
        }
    }

    fun updateTextField(textField: TextFieldValue = TextFieldValue("")) {
        _state.update {
            it.copy(
                testTextField = textField,
            )
        }
    }
}

sealed class GoalListEffect {
    data object NavigateToCreateGoal : GoalListEffect()
}