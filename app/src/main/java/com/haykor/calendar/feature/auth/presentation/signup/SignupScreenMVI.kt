package com.haykor.calendar.feature.auth.presentation.signup

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.haykor.calendar.core.common.presentation.model.UiText
import com.haykor.calendar.feature.auth.presentation.error.EmailError
import com.haykor.calendar.feature.auth.presentation.error.NameError
import com.haykor.calendar.feature.auth.presentation.error.PasswordError

sealed interface SignupScreenEvent {
    data object NavigateToMain : SignupScreenEvent

    data class ShowError(
        val message: UiText,
    ) : SignupScreenEvent
}

sealed interface SignupScreenIntent {
    data object TrySignup : SignupScreenIntent

    data object TryGoogleSignUp : SignupScreenIntent

    data object NavigateToLogin : SignupScreenIntent
}

@Stable
data class SignupState(
    val email: TextFieldState = TextFieldState(),
    val emailError: EmailError? = null,
    val password: TextFieldState = TextFieldState(),
    val passwordError: PasswordError? = null,
    //Добавленные стейты для регистрации по мимо логина
    val passwordConfirm: TextFieldState = TextFieldState(),
    val passwordConfirmError: PasswordError? = null,
    // Имя опционально
    val name: TextFieldState = TextFieldState(),
    val nameError: NameError? = null,
    //
    val isLoading: Boolean = false,
)
