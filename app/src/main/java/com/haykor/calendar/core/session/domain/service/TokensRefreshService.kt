package com.haykor.calendar.core.session.domain.service

import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.Tokens
import com.haykor.calendar.core.session.domain.model.SessionError

interface TokensRefreshService {
    suspend fun refreshTokens(refreshToken: String): DataResult<Tokens, SessionError>
}
