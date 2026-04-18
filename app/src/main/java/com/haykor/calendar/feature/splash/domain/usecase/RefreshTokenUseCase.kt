package com.haykor.calendar.feature.splash.domain.usecase

import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.map
import com.haykor.calendar.core.data.local.datastore.TokenManager
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.auth.domain.service.AuthService

class RefreshTokenUseCase(
    private val tokenManager: TokenManager,
    private val authService: AuthService,
) {
    suspend operator fun invoke(refreshToken: String): DataResult<Unit, AuthError> =
        when (val result = authService.refreshTokens(refreshToken)) {
            is DataResult.Success -> {
                tokenManager.saveTokens(result.data)
                result.map { Unit }
            }

            is DataResult.Error -> {
                result
            }
        }
}
