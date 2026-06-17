package com.haykor.calendar.feature.auth.presentation.mapper


import com.haykor.calendar.R
import com.haykor.calendar.core.common.presentation.model.UiText
import com.haykor.calendar.feature.auth.domain.model.SignupError

fun SignupError.toUiText(): UiText =
    when (this) {
        SignupError.UserExists -> UiText.StringResource(R.string.user_exists)
        SignupError.NetworkError -> UiText.StringResource(R.string.error_network)
        SignupError.UnknownError -> UiText.StringResource(R.string.error_unknown)
    }