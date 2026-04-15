package com.haykor.calendar.feature.auth.domain.usecase

import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.Tokens
import com.haykor.calendar.core.data.local.datastore.TokenManager
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.auth.domain.service.AuthService
import okio.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class LoginUseCase(
    private val authService: AuthService,
    private val tokenManager: TokenManager,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): DataResult<Tokens, AuthError> =
        try {
            when (val result = authService.login(email, password)) {
                is DataResult.Error -> {
                    result
                }

                is DataResult.Success -> {
                    tokenManager.saveTokens(result.data)
                    result
                }
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
