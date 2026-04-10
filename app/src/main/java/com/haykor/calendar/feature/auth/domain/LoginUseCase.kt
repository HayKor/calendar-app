package com.haykor.calendar.feature.auth.domain

import com.haykor.calendar.core.common.domain.DataResult
import com.haykor.calendar.core.common.domain.Tokens
import okio.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class LoginUseCase(
    private val authService: AuthService,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): DataResult<Tokens, AuthError> =
        try {
            when (val result = authService.login(email, password)) {
                is DataResult.Error -> result
                is DataResult.Success -> result
            }
        } catch (e: IOException) {
            val authError =
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is SocketTimeoutException,
                    -> AuthError.NetworkError

                    else -> AuthError.UnknownError
                }
            DataResult.Error(authError)
        }
}
