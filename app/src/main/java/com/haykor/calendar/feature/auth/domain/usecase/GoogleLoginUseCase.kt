package com.haykor.calendar.feature.auth.domain.usecase

import android.util.Log
import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.Tokens
import com.haykor.calendar.core.data.local.datastore.TokenManager
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.auth.domain.service.AuthService
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class GoogleLoginUseCase(
    private val authService: AuthService,
    private val tokenManager: TokenManager,
) {
    suspend operator fun invoke(idToken: String): DataResult<Tokens, AuthError> {
        Log.d(TAG, "Google sign-in started")
        return try {
            when (val result = authService.signInWithGoogle(idToken)) {
                is DataResult.Error -> {
                    Log.e(TAG, "Google sign-in failed — error=${result.error}")
                    result
                }

                is DataResult.Success -> {
                    Log.d(TAG, "Google sign-in succeeded — saving tokens")
                    tokenManager.saveTokens(result.data)
                    Log.d(TAG, "Tokens saved")
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
            Log.e(
                TAG,
                "Google sign-in IOException — type=${e::class.simpleName}, resolvedError=$authError",
                e,
            )
            DataResult.Error(authError)
        }
    }

    companion object {
        const val TAG = "GoogleLoginUseCase"
    }
}
