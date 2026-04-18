package com.haykor.calendar.feature.auth.presentation.mapper

import com.haykor.calendar.R
import com.haykor.calendar.core.common.presentation.model.UiText
import com.haykor.calendar.feature.auth.domain.model.AuthError

fun AuthError.toUiText(): UiText =
    when (this) {
        AuthError.UserNotFound -> UiText.StringResource(R.string.user_not_found)
    }
