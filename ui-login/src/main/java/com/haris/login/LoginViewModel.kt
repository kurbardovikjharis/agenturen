package com.haris.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haris.domain.ObservableLoadingCounter
import com.haris.domain.collectStatus
import com.haris.login.interactors.LoginInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val loginInteractor: LoginInteractor
) : ViewModel() {

    private val counter = ObservableLoadingCounter()

    private val eventsChannel = Channel<SingleEvent>(Channel.BUFFERED)
    val singleEvent: Flow<SingleEvent> = eventsChannel.receiveAsFlow()

    private val email = MutableStateFlow("")
    private val password = MutableStateFlow("")

    val state: StateFlow<LoginViewState> =
        combine(email, password, counter.observable) { email, password, isLoading ->
            LoginViewState(
                email = email,
                password = password,
                isLoading = isLoading,
                isButtonEnabled = isButtonEnabled(email, password)
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = LoginViewState.Empty,
        )

    fun updateEmail(value: String) {
        email.value = value
    }

    fun updatePassword(value: String) {
        password.value = value
    }

    fun login(onLogin: () -> Unit) {
        viewModelScope.launch {
            loginInteractor(
                LoginInteractor.Params(email.value, password.value)
            ).collectStatus(
                counter,
                onSuccess = {
                    onLogin()
                }, onError = {
                    viewModelScope.launch { eventsChannel.send(SingleEvent.Error) }
                })
        }
    }

    private fun isButtonEnabled(email: String, password: String): Boolean {
        val isEmailValid = if (email.isEmpty()) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        return isEmailValid && password.length > 5
    }
}

internal sealed class SingleEvent {
    object Error : SingleEvent()
}