package com.haykor.calendar.feature.auth.domain.service

import com.haykor.calendar.core.common.domain.DataResult
import com.haykor.calendar.core.common.domain.Tokens
import com.haykor.calendar.feature.auth.domain.model.AuthError

interface AuthService {
    suspend fun refreshTokens(refreshToken: String): DataResult<Tokens, AuthError>

    suspend fun login(
        email: String,
        password: String,
    ): DataResult<Tokens, AuthError>
}
