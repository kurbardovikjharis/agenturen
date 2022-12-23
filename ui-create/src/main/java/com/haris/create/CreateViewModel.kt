package com.haris.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haris.base.combine
import com.haris.data.entities.Type
import com.haris.domain.ObservableLoadingCounter
import com.haris.domain.collectStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
internal class CreateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addTodo: AddTodo,
    private val getTodo: GetTodo
) : ViewModel() {

    private val id = savedStateHandle.get<Long>("id") ?: -1L

    private val isUpdate = MutableStateFlow(false)
    private val title = MutableStateFlow("")
    private val description = MutableStateFlow("")
    private val time = MutableStateFlow<LocalTime?>(null)
    private val date = MutableStateFlow<LocalDate?>(null)
    private val type = MutableStateFlow(Type.Daily)

    init {
        if (id != -1L) {
            isUpdate.value = true
            viewModelScope.launch {
                getTodo(id).collectLatest {
                    title.value = it.title
                    description.value = it.description
                    time.value = it.time
                    date.value = it.date
                    type.value = it.type
                }
            }
        }
    }

    val state: StateFlow<CreateViewState> =
        combine(
            title,
            description,
            time,
            date,
            type,
            isUpdate
        ) { title, description, time, date, type, isUpdate ->
            CreateViewState(
                title = title,
                description = description,
                time = time,
                date = date,
                type = type,
                isUpdate = isUpdate,
                enabled = title.isNotEmpty() && time != null
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

    fun updateTime(value: LocalTime) {
        time.value = value
    }

    fun updateDate(value: LocalDate) {
        date.value = value
    }

    fun save() {
        viewModelScope.launch {
            addTodo(
                AddTodo.Params(
                    id = id,
                    title = title.value,
                    description = description.value,
                    time = time.value,
                    date = date.value,
                    type = type.value
                )
            ).collectStatus(ObservableLoadingCounter())
        }
    }
}