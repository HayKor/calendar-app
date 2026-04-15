package com.haykor.calendar.feature.auth.presentation.validation

object AndroidEmailFormatChecker : EmailFormatChecker {
    override fun isValid(email: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()
}
