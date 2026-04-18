package com.haykor.calendar.feature.auth.domain.model

import com.haykor.calendar.core.common.domain.model.RootError

sealed interface AuthError : RootError {
    data object Unauthorized : AuthError

    data object UserNotFound : AuthError

    data object NetworkError : AuthError

    data object UnknownError : AuthError
}
