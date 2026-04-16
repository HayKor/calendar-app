package com.haykor.calendar.feature.auth.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleIdTokenRequest(
    @SerialName("id_token") val idToken: String,
)
