package com.haykor.calendar.feature.auth.domain

import com.haykor.calendar.core.common.domain.DataResult
import com.haykor.calendar.core.common.domain.Tokens

interface AuthService {
    suspend fun refreshTokens(refreshToken: String): DataResult<Tokens, AuthError>

    suspend fun login(
        email: String,
        password: String,
    ): DataResult<Tokens, AuthError>
}
