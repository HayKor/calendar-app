package com.haykor.calendar.feature.auth.data.mapper

import com.haykor.calendar.core.common.domain.Tokens
import com.haykor.calendar.feature.auth.data.model.AuthResponse

internal fun AuthResponse.toDomain() =
    Tokens(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        accessTokenExpiresIn = this.accessTokenExpiresIn,
        refreshTokenExpiresIn = this.refreshTokenExpiresIn,
    )
