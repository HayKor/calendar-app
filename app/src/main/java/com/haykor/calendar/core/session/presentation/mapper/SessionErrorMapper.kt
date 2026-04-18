package com.haykor.calendar.core.session.presentation.mapper

import com.haykor.calendar.R
import com.haykor.calendar.core.common.presentation.model.UiText
import com.haykor.calendar.core.session.domain.model.SessionError

fun SessionError.toUiText() =
    when (this) {
        SessionError.NetworkError -> UiText.StringResource(R.string.error_network)

        SessionError.UnknownError -> UiText.StringResource(R.string.error_unknown)

        SessionError.SessionExpired,
        -> UiText.StringResource(R.string.error_session_expired)
    }
