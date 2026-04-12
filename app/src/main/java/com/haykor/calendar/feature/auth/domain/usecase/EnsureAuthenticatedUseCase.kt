package com.haykor.calendar.feature.auth.domain.usecase

import com.haykor.calendar.core.common.domain.DataResult
import com.haykor.calendar.core.common.domain.flatMap
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.auth.domain.model.AuthStatus

class EnsureAuthenticatedUseCase(
    private val getAuthStatusUseCase: GetAuthStatusUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
) {
    suspend operator fun invoke(): DataResult<Unit, AuthError> =
        getAuthStatusUseCase().flatMap { status ->
            when (status) {
                AuthStatus.Authorized -> {
                    DataResult.Success(Unit)
                }

                AuthStatus.Unauthorized -> {
                    DataResult.Error(AuthError.SessionExpired)
                }

                is AuthStatus.CanRefreshTokens -> {
                    refreshTokenUseCase(status.refreshToken)
                }
            }
        }
}
