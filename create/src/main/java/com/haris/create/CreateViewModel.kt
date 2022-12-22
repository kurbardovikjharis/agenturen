package com.haris.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haris.base.ObservableLoadingCounter
import com.haris.base.collectStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CreateViewModel @Inject constructor(
    private val addTodo: AddTodo
) : ViewModel() {

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
        viewModelScope.launch {
            addTodo(text.value).collectStatus(ObservableLoadingCounter())
        }
    }
}