package com.mr.presentation.home.today

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

data class TodayState(
    val date: Date = Date(),
)

@HiltViewModel
class TodayViewModel @Inject constructor() : ViewModel(), AddActionHandler {

    private val _state = MutableStateFlow(TodayState())
    val state = _state.asStateFlow()

    private val _effect = Channel<TodayEffect>()
    val effect = _effect.receiveAsFlow()

    override fun onAdd() {
        viewModelScope.launch {
            _effect.send(TodayEffect.NavigateToTaskCreate)
        }
    }
}

sealed class TodayEffect {
    data object NavigateToTaskCreate : TodayEffect()
}