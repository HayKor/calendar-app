package com.haykor.calendar.core.session.domain.model

sealed interface SessionStatus {
    data object Authorized : SessionStatus

    data class CanRefreshTokens(
        val refreshToken: String,
    ) : SessionStatus

    data object Unauthorized : SessionStatus
}
