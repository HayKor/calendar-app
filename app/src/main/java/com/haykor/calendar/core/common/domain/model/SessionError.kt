package com.haykor.calendar.core.common.domain.model

sealed interface SessionError : RootError {
    data object NetworkError : SessionError

    data object UnknownError : SessionError

    data object SessionExpired : SessionError
}
