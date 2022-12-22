package com.haris.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class CreateViewModel @Inject constructor() : ViewModel() {

    private val text = MutableStateFlow("")

    val state: StateFlow<CreateViewState> = text.map { text ->
        CreateViewState(
            value = text,
            isButtonEnabled = text.isEmpty()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CreateViewState.Empty
    )

    fun updateText(value: String) {
        text.value = value
    }

    fun save() {

    }
}