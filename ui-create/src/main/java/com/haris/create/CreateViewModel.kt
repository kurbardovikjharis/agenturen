package com.haris.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haris.base.combine
import com.haris.create.interactors.AddTodo
import com.haris.create.interactors.GetTodo
import com.haris.data.entities.Type
import com.haris.domain.ObservableLoadingCounter
import com.haris.domain.collectStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
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

    private val eventsChannel = Channel<SingleEvent>(Channel.BUFFERED)
    val singleEvent: Flow<SingleEvent> = eventsChannel.receiveAsFlow()

    private val counter = ObservableLoadingCounter()

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
                    description.value = it.description ?: ""
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
            isUpdate,
            counter.observable
        ) { title, description, time, date, type, isUpdate, isLoading ->
            CreateViewState(
                title = title,
                description = description,
                time = time,
                date = date,
                type = type,
                isUpdate = isUpdate,
                enabled = title.isNotEmpty() && time != null,
                isLoading = isLoading
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

    fun updateTime(value: LocalTime) {
        time.value = value
    }

    fun updateDate(value: LocalDate) {
        date.value = value
    }

    fun updateType(value: Type) {
        type.value = value
    }

    fun save(onDone: () -> Unit) {
        viewModelScope.launch {
            addTodo(
                AddTodo.Params(
                    id = id,
                    title = title.value,
                    description = description.value,
                    time = time.value ?: LocalTime.now(),
                    date = date.value,
                    type = type.value
                )
            ).collectStatus(
                counter,
                onSuccess = onDone,
                onError = {
                    viewModelScope.launch {
                        eventsChannel.send(SingleEvent.Error)
                    }
                })
        }
    }
}

internal sealed class SingleEvent {
    object Error : SingleEvent()
}