package com.haris.login

import javax.annotation.concurrent.Immutable

@Immutable
internal data class LoginViewState(
    val email: String = "",
    val password: String = "",
    val isButtonEnabled: Boolean = false
) {

    companion object {
        val Empty = LoginViewState()
    }
}