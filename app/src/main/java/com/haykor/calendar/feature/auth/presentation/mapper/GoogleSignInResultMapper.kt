package com.haykor.calendar.feature.auth.presentation.mapper

import com.haykor.calendar.R
import com.haykor.calendar.core.common.presentation.model.UiText
import com.haykor.calendar.feature.auth.domain.model.GoogleSignInResult

fun GoogleSignInResult.toUiText() =
    when (this) {
        GoogleSignInResult.Cancelled -> {
            UiText.StringResource(R.string.google_sign_in_cancelled)
        }

        GoogleSignInResult.Failure -> {
            UiText.StringResource(R.string.google_sign_in_failed)
        }

        is GoogleSignInResult.Success -> {
            UiText.StringResource(R.string.google_sign_in_succeeded)
        }
    }
