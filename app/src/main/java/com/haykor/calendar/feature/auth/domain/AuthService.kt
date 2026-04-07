package com.haykor.calendar.feature.auth.domain

import com.haykor.calendar.core.common.domain.DataResult

interface AuthService {
    suspend fun refreshTokens(refreshToken: String): DataResult<Unit, AuthError>
}
