package com.haykor.calendar.feature.auth.presentation.error

import androidx.compose.runtime.Stable

@Stable
sealed interface EmailError {
    data object Empty : EmailError

    data object Invalid : EmailError
}

@Stable
sealed interface PasswordError {
    data object Empty : PasswordError

    data object Invalid : PasswordError

    data object TooShort : PasswordError
}
