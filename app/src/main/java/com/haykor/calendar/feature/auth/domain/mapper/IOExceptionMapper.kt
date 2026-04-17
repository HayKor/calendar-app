package com.haykor.calendar.feature.auth.domain.mapper

import com.haykor.calendar.feature.auth.domain.model.AuthError
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun IOException.toAuthError(): AuthError =
    when (this) {
        is UnknownHostException,
        is ConnectException,
        is SocketTimeoutException,
        -> AuthError.NetworkError

        else -> AuthError.UnknownError
    }
