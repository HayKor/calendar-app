package com.haykor.calendar.feature.auth.domain.usecase

import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.Tokens
import com.haykor.calendar.core.session.domain.repository.TokenManager
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.auth.domain.model.SignupError
import com.haykor.calendar.feature.auth.domain.service.AuthService

class SignupUseCase(
    private val authService: AuthService,
    private val tokenManager: TokenManager,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String
    ): DataResult<Tokens, SignupError> =
        //TODO заменить затычку на правильную реализуцию
        when (val result = authService.register(email, password, name)) {
            is DataResult.Success-> {
                tokenManager.saveTokens(result.data)
                result
            }

            is DataResult.Error -> {
                result
            }
        }
}