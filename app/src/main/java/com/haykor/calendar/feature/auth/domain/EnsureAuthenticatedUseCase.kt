package com.haykor.calendar.feature.auth.domain

import com.haykor.calendar.core.common.domain.DataResult
import com.haykor.calendar.core.common.domain.flatMap
import com.haykor.calendar.core.common.domain.map

class EnsureAuthenticatedUseCase(
    private val getAuthStatusUseCase: GetAuthStatusUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
) {
    suspend operator fun invoke(): DataResult<Boolean, AuthError> =
        getAuthStatusUseCase().flatMap { status ->
            when (status) {
                AuthStatus.Authorized -> {
                    DataResult.Success(true)
                }

                AuthStatus.Unauthorized -> {
                    DataResult.Success(false)
                }

                is AuthStatus.CanRefreshTokens -> {
                    refreshTokenUseCase(status.refreshToken).map { true }
                }
            }
        }
}
