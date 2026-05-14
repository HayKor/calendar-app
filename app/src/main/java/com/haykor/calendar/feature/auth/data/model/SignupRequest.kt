package com.haykor.calendar.feature.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest (
    val name: String,
    val email: String,
    val password: String,
)