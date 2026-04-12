package com.haykor.calendar.feature.auth.domain.model

import com.haykor.calendar.core.common.domain.RootError

sealed interface AuthError : RootError {
    object NetworkError : AuthError

    object UnknownError : AuthError

    object Unauthorized : AuthError // HTTP 401

    object SessionExpired : AuthError

    object UserNotFound : AuthError // HTTP 404
}
