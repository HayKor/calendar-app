package com.haykor.calendar.feature.auth.domain

sealed interface AuthStatus {
    object Authorized : AuthStatus

    data class CanRefreshTokens(
        val refreshToken: String,
    ) : AuthStatus

    object Unauthorized : AuthStatus
}
