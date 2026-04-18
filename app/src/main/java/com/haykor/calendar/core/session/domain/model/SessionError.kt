package com.haykor.calendar.core.session.domain.model

import com.haykor.calendar.core.common.domain.model.RootError

sealed interface SessionError : RootError {
    data object NetworkError : SessionError

    data object UnknownError : SessionError

    data object SessionExpired : SessionError
}
