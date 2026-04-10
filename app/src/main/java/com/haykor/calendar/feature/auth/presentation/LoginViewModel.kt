package com.haykor.calendar.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _event = Channel<LoginScreenEvent>()
    val event = _event.receiveAsFlow()

    fun onIntent(intent: LoginScreenIntent) {
        when (intent) {
            LoginScreenIntent.TryLogin -> tryLogin()
        }
    }

    private fun tryLogin() =
        viewModelScope.launch {
        }
}
