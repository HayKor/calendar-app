package com.haykor.calendar.feature.auth.domain.usecase

import com.haykor.calendar.core.common.domain.DataResult
import com.haykor.calendar.core.data.local.datastore.TokenManager
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.auth.domain.model.AuthStatus

class GetAuthStatusUseCase(
    private val tokenManager: TokenManager,
) {
    suspend operator fun invoke(): DataResult<AuthStatus, AuthError> {
        val refreshToken = tokenManager.getRefreshToken()

        return when {
            hasValidAccessToken() -> {
                DataResult.Success(AuthStatus.Authorized)
            }

            !tokenManager.isRefreshTokenExpired() && refreshToken != null -> {
                DataResult.Success(AuthStatus.CanRefreshTokens(refreshToken))
            }

            else -> {
                tokenManager.clearTokens()
                DataResult.Success(AuthStatus.Unauthorized)
            }
        }
    }

    private suspend fun hasValidAccessToken(): Boolean = !tokenManager.isAccessTokenExpired()
}
