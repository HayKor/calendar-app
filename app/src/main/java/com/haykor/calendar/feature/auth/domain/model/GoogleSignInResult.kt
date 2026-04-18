package com.haykor.calendar.feature.auth.domain.model

sealed interface GoogleSignInResult { // TODO: is it ok to have such class when we have DataResult<R, E>?
    data class Success(
        val idToken: String,
    ) : GoogleSignInResult

    data object Cancelled : GoogleSignInResult

    data object Failure : GoogleSignInResult
}
