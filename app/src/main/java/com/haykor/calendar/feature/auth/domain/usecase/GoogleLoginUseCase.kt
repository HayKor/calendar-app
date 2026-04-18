package com.haykor.calendar.feature.auth.domain.usecase

import android.util.Log
import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.Tokens
import com.haykor.calendar.core.session.domain.repository.TokenManager
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.auth.domain.service.AuthService

class GoogleLoginUseCase(
    private val authService: AuthService,
    private val tokenManager: TokenManager,
) {
    suspend operator fun invoke(idToken: String): DataResult<Tokens, AuthError> {
        Log.d(TAG, "Google sign-in started")
        return when (val result = authService.signInWithGoogle(idToken)) {
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
    }

    companion object {
        const val TAG = "GoogleLoginUseCase"
    }
}
