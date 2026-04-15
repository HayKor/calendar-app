package com.haykor.calendar.feature.auth.presentation.validation

interface EmailFormatChecker {
    fun isValid(email: String): Boolean
}
