package com.haykor.calendar.feature.auth.domain

import com.haykor.calendar.core.common.domain.DataResult
import com.haykor.calendar.core.data.local.datastore.TokenManager
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RefreshTokenUseCase(
    private val tokenManager: TokenManager,
    private val authService: AuthService,
) {
    suspend operator fun invoke(refreshToken: String): DataResult<Unit, AuthError> {
        val refreshToken =
            tokenManager.getRefreshToken() ?: return DataResult.Error(AuthError.UnknownError)

        return try {
            when (val result = authService.refreshTokens(refreshToken)) {
                is DataResult.Success -> {
                    result
                }

                is DataResult.Error -> {
                    tokenManager.clearTokens()
                    result
                }
            }
        } catch (e: IOException) {
            tokenManager.clearTokens()
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
}
