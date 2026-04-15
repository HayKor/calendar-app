package com.haykor.calendar.feature.auth.presentation.validation

import com.haykor.calendar.feature.auth.presentation.error.PasswordError

object PasswordValidator {
    operator fun invoke(password: String): PasswordError? =
        when {
            password.isBlank() -> PasswordError.Empty
            !isValidChars(password) -> PasswordError.Invalid
            password.length < MIN_LENGTH -> PasswordError.TooShort
            else -> null
        }

    private fun isValidChars(password: String): Boolean = password.matches(Regex("^[A-Za-z0-9]+$"))

    private const val MIN_LENGTH = 6
}
