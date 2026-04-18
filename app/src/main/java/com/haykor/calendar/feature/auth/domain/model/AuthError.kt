package com.haykor.calendar.feature.auth.domain.model

import com.haykor.calendar.core.common.domain.model.RootError

sealed interface AuthError : RootError {
    object UserNotFound : AuthError // HTTP 404
}
