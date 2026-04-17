package com.haykor.calendar.feature.auth.domain.model

sealed interface GoogleSignInResult {
    data class Success(
        val idToken: String,
    ) : GoogleSignInResult

    data object Cancelled : GoogleSignInResult

    data object Failure : GoogleSignInResult
}
