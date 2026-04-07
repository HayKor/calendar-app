package com.haykor.calendar.feature.auth.domain

import com.haykor.calendar.core.common.domain.RootError

sealed interface AuthError : RootError {
    object NetworkError : AuthError

    object UnknownError : AuthError
}
