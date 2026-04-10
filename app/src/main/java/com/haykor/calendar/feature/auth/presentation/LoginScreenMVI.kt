package com.haykor.calendar.feature.auth.presentation

import androidx.compose.runtime.Stable
import com.haykor.calendar.core.common.presentation.UiText

sealed interface LoginScreenEvent {
    data object NavigateToMain : LoginScreenEvent
}

sealed interface LoginScreenIntent {
    data object TryLogin : LoginScreenIntent
}

@Stable
data class LoginState(
    val email: String = "",
    val password: String = "",
    val error: UiText? = null,
    val isLoading: Boolean = false,
)
