package com.haykor.calendar.core.common.domain.model

sealed interface SessionStatus {
    data object Authorized : SessionStatus

    data class CanRefreshTokens(
        val refreshToken: String,
    ) : SessionStatus

    data object Unauthorized : SessionStatus
}
