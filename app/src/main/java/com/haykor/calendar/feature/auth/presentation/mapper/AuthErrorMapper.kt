package com.haykor.calendar.feature.auth.presentation.mapper

import com.haykor.calendar.R
import com.haykor.calendar.core.common.presentation.UiText
import com.haykor.calendar.feature.auth.domain.AuthError

fun AuthError.toUiText(): UiText =
    when (this) {
        AuthError.NetworkError -> UiText.StringResource(R.string.error_network)

        AuthError.UnknownError -> UiText.StringResource(R.string.error_unknown)

        AuthError.SessionExpired,
        AuthError.Unauthorized,
        -> UiText.StringResource(R.string.error_session_expired)
    }
