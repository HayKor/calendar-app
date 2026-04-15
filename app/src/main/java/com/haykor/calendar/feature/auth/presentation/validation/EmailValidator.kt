package com.haykor.calendar.feature.auth.presentation.validation

import com.haykor.calendar.feature.auth.presentation.error.EmailError

class EmailValidator(
    private val emailFormatChecker: EmailFormatChecker,
) {
    operator fun invoke(email: String): EmailError? =
        when {
            email.isBlank() -> EmailError.Empty
            !emailFormatChecker.isValid(email) -> EmailError.Invalid
            else -> null
        }
}
