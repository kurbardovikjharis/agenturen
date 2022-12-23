package com.haris.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val loginInteractor: LoginInteractor
) : ViewModel() {

    private val eventsChannel = Channel<SingleEvent>(Channel.BUFFERED)
    val singleEvent: Flow<SingleEvent> = eventsChannel.receiveAsFlow()

    private val email = MutableStateFlow("")
    private val password = MutableStateFlow("")

    val state: StateFlow<LoginViewState> = combine(email, password) { email, password ->
        LoginViewState(
            email = email,
            password = password,
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
            val result = loginInteractor.executeSync(
                LoginInteractor.Params(email.value, password.value)
            )

            onLogin()

//            if (result.isEmpty()) {
//                eventsChannel.send(SingleEvent.Error)
//            } else {
//                onLogin()
//            }
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