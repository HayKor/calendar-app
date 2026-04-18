package com.haykor.calendar.feature.auth.domain.usecase

import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.Tokens
import com.haykor.calendar.core.session.domain.repository.TokenManager
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.auth.domain.service.AuthService

class LoginUseCase(
    private val authService: AuthService,
    private val tokenManager: TokenManager,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): DataResult<Tokens, AuthError> =
        when (val result = authService.login(email, password)) {
            is DataResult.Success -> {
                tokenManager.saveTokens(result.data)
                result
            }

            is DataResult.Error -> {
                result
            }
        }
}
