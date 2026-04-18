package com.haykor.calendar.core.session.domain.service

import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.SessionError
import com.haykor.calendar.core.common.domain.model.Tokens

interface TokensRefreshService {
    suspend fun refreshTokens(refreshToken: String): DataResult<Tokens, SessionError>
}
