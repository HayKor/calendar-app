package com.haykor.calendar.feature.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.haykor.calendar.core.common.presentation.model.UiText
import com.haykor.calendar.feature.auth.presentation.error.EmailError
import com.haykor.calendar.feature.auth.presentation.error.PasswordError

sealed interface LoginScreenEvent {
    data object NavigateToMain : LoginScreenEvent
}

sealed interface LoginScreenIntent {
    data object TryLogin : LoginScreenIntent

    data class TryGoogleSignIn(
        val idToken: String,
    ) : LoginScreenIntent

    data object NavigateToSignup : LoginScreenIntent
}

@Stable
data class LoginState(
    val email: TextFieldState = TextFieldState(),
    val emailError: EmailError? = null,
    val password: TextFieldState = TextFieldState(),
    val passwordError: PasswordError? = null,
    val error: UiText? = null,
    val isLoading: Boolean = false,
)
