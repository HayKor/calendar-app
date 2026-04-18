package com.haykor.calendar.core.session.domain.usecase

import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.SessionError
import com.haykor.calendar.core.common.domain.model.map
import com.haykor.calendar.core.data.local.datastore.TokenManager
import com.haykor.calendar.core.session.domain.service.TokensRefreshService

class RefreshTokenUseCase(
    private val tokenManager: TokenManager,
    private val tokensRefreshService: TokensRefreshService,
) {
    suspend operator fun invoke(refreshToken: String): DataResult<Unit, SessionError> =
        when (val result = tokensRefreshService.refreshTokens(refreshToken)) {
            is DataResult.Success -> {
                tokenManager.saveTokens(result.data)
                result.map { Unit }
            }

            is DataResult.Error -> {
                result
            }
        }
}
