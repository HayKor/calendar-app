package com.haykor.calendar.feature.auth.presentation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.haykor.calendar.core.common.presentation.UiText

sealed interface LoginScreenEvent {
    data object NavigateToMain : LoginScreenEvent
}

sealed interface LoginScreenIntent {
    data object TryLogin : LoginScreenIntent

    data object NavigateToSignup : LoginScreenIntent
}

@Stable
data class LoginState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val error: UiText? = null,
    val isLoading: Boolean = false,
)
