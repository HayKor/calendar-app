package com.haykor.calendar.core.session.domain.usecase

import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.SessionError
import com.haykor.calendar.core.common.domain.model.SessionStatus
import com.haykor.calendar.core.data.local.datastore.TokenManager

class GetAuthStatusUseCase(
    private val tokenManager: TokenManager,
) {
    suspend operator fun invoke(): DataResult<SessionStatus, SessionError> {
        val refreshToken = tokenManager.getRefreshToken()

        return when {
            hasValidAccessToken() -> {
                DataResult.Success(SessionStatus.Authorized)
            }

            !tokenManager.isRefreshTokenExpired() && refreshToken != null -> {
                DataResult.Success(SessionStatus.CanRefreshTokens(refreshToken))
            }

            else -> {
                tokenManager.clearTokens()
                DataResult.Success(SessionStatus.Unauthorized)
            }
        }
    }

    private suspend fun hasValidAccessToken(): Boolean = !tokenManager.isAccessTokenExpired()
}
