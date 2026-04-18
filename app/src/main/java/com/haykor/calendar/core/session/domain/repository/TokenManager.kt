package com.haykor.calendar.core.session.domain.repository

import com.haykor.calendar.core.common.domain.model.Tokens

interface TokenManager {
    suspend fun saveTokens(tokens: Tokens)

    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?

    suspend fun isAccessTokenExpired(): Boolean

    suspend fun isRefreshTokenExpired(): Boolean

    suspend fun clearTokens()
}
