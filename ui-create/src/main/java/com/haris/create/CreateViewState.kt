package com.haris.create

import com.haris.data.entities.Type
import javax.annotation.concurrent.Immutable

@Immutable
internal data class CreateViewState(
    val title: String = "",
    val description: String = "",
    val time: String = "",
    val date: String = "",
    val type: Type = Type.Daily,
    val enabled: Boolean = false
) {

    companion object {
        val Empty = CreateViewState()
    }
}