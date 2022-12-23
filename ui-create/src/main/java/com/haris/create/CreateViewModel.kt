package com.haris.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haris.data.entities.Type
import com.haris.domain.ObservableLoadingCounter
import com.haris.domain.collectStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CreateViewModel @Inject constructor(
    private val addTodo: AddTodo
) : ViewModel() {

    private val title = MutableStateFlow("")
    private val description = MutableStateFlow("")
    private val type = MutableStateFlow(Type.Daily)

    val state: StateFlow<CreateViewState> =
        combine(title, description, type) { title, description, type ->
            CreateViewState(
                title = title,
                description = description,
                type = type,
                enabled = title.isNotEmpty() && description.isNotEmpty()
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = CreateViewState.Empty
        )

    fun updateTitle(value: String) {
        title.value = value
    }

    fun updateDescription(value: String) {
        description.value = value
    }

    fun updateType(value: Type) {
        type.value = value
    }

    fun save() {
        viewModelScope.launch {
            addTodo(
                AddTodo.Params(
                    title = title.value,
                    description = description.value,
                    type = type.value
                )
            ).collectStatus(ObservableLoadingCounter())
        }
    }
}