package com.haris.create

import com.haris.data.entities.Type
import java.time.LocalDate
import java.time.LocalTime
import javax.annotation.concurrent.Immutable

@Immutable
internal data class CreateViewState(
    val title: String = "",
    val description: String = "",
    val time: LocalTime? = null,
    val date: LocalDate? = null,
    val type: Type = Type.Daily,
    val isUpdate: Boolean = false,
    val enabled: Boolean = false
) {

    companion object {
        val Empty = CreateViewState()
    }
}