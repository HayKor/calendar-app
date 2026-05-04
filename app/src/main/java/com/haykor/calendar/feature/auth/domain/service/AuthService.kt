package com.haykor.calendar.feature.auth.domain.service

import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.Tokens
import com.haykor.calendar.core.session.domain.service.TokensRefreshService
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.auth.domain.model.SignupError

interface AuthService : TokensRefreshService {
    suspend fun login(
        email: String,
        password: String,
    ): DataResult<Tokens, AuthError>

    // Отдельная функция для регистрации
    //TODO Надо ли отдельно создавать интерфейс под нее???
    suspend fun register(
        email: String,
        password: String,
        name: String
    ): DataResult<Tokens, SignupError>

    suspend fun signInWithGoogle(idToken: String): DataResult<Tokens, AuthError>
}
