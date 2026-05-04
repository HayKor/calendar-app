package com.haykor.calendar.feature.auth.domain.model

import com.haykor.calendar.core.common.domain.model.RootError

sealed interface SignupError : RootError {
    data object UserExists : SignupError

    //TODO дописать возможные ошибки регистрации

    data object NetworkError : SignupError

    data object UnknownError : SignupError
}