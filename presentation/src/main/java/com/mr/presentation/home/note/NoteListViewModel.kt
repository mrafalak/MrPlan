package com.mr.presentation.home.note

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
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


@HiltViewModel
class NoteListViewModel @Inject constructor() : ViewModel(), AddActionHandler {

    private val _effect = Channel<NoteListEffect>()
    val effect = _effect.receiveAsFlow()

    override fun onAdd() {
        viewModelScope.launch {
            _effect.send(NoteListEffect.NavigateToCreateNote)
        }
    }
}

sealed class NoteListEffect {
    data object NavigateToCreateNote : NoteListEffect()
}