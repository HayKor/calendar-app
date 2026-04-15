package com.haykor.calendar.feature.auth.presentation.mapper

import com.haykor.calendar.R
import com.haykor.calendar.core.common.presentation.model.UiText
import com.haykor.calendar.feature.auth.presentation.error.PasswordError

fun PasswordError.toUiText() =
    when (this) {
        PasswordError.Empty -> UiText.StringResource(R.string.password_cannot_be_empty)
        PasswordError.Invalid -> UiText.StringResource(R.string.invalid_password)
        PasswordError.TooShort -> UiText.StringResource(R.string.too_short_password)
    }
