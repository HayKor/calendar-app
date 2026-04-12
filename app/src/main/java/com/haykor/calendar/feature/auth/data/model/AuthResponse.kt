package com.haykor.calendar.feature.auth.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("access_token_expires_in") val accessTokenExpiresIn: Long,
    @SerialName("refresh_token_expires_in") val refreshTokenExpiresIn: Long,
)
