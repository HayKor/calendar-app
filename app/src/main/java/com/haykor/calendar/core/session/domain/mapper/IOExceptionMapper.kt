package com.haykor.calendar.core.session.domain.mapper

import com.haykor.calendar.core.session.domain.model.SessionError
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun IOException.toSessionError(): SessionError =
    when (this) {
        is UnknownHostException,
        is ConnectException,
        is SocketTimeoutException,
        -> SessionError.NetworkError

        else -> SessionError.UnknownError
    }
