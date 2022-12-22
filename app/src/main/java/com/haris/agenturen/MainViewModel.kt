package com.haris.agenturen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getToken: GetToken
) : ViewModel() {

    val isLoggedIn = MutableStateFlow(true)

    init {
        viewModelScope.launch {
            getToken(Unit).collectLatest {
                isLoggedIn.value = it.isNotEmpty()
            }
        }
    }

    fun setLoggedIn(value: Boolean) {
        isLoggedIn.value = value
    }
}