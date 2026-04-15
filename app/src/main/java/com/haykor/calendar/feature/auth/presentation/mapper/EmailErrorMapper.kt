package com.haykor.calendar.feature.auth.presentation.mapper

import com.haykor.calendar.R
import com.haykor.calendar.core.common.presentation.model.UiText
import com.haykor.calendar.feature.auth.presentation.error.EmailError

fun EmailError.toUiText() =
    when (this) {
        EmailError.Empty -> UiText.StringResource(R.string.email_cannot_be_empty)
        EmailError.Invalid -> UiText.StringResource(R.string.invalid_email)
    }
