package com.haris.create

import javax.annotation.concurrent.Immutable

@Immutable
internal data class CreateViewState(
    val value: String = "",
    val isButtonEnabled: Boolean = false
) {

    companion object {
        val Empty = CreateViewState()
    }
}