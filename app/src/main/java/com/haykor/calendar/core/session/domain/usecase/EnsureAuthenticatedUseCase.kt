package com.haykor.calendar.core.session.domain.usecase

import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.SessionError
import com.haykor.calendar.core.common.domain.model.SessionStatus
import com.haykor.calendar.core.common.domain.model.flatMap

class EnsureAuthenticatedUseCase(
    private val getAuthStatusUseCase: GetAuthStatusUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
) {
    suspend operator fun invoke(): DataResult<Unit, SessionError> =
        getAuthStatusUseCase().flatMap { status ->
            when (status) {
                SessionStatus.Authorized -> {
                    DataResult.Success(Unit)
                }

                SessionStatus.Unauthorized -> {
                    DataResult.Error(SessionError.SessionExpired)
                }

                is SessionStatus.CanRefreshTokens -> {
                    refreshTokenUseCase(status.refreshToken)
                }
            }
        }
}
