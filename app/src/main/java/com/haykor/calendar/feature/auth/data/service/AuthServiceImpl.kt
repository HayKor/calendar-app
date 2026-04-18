package com.haykor.calendar.feature.auth.data.service

import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.SessionError
import com.haykor.calendar.core.common.domain.model.Tokens
import com.haykor.calendar.core.session.domain.mapper.toSessionError
import com.haykor.calendar.feature.auth.data.mapper.toDomain
import com.haykor.calendar.feature.auth.data.model.AuthResponse
import com.haykor.calendar.feature.auth.data.model.GoogleIdTokenRequest
import com.haykor.calendar.feature.auth.data.model.LoginRequest
import com.haykor.calendar.feature.auth.domain.mapper.toAuthError
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.auth.domain.service.AuthService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.cookie
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import java.io.IOException

class AuthServiceImpl(
    private val httpClient: HttpClient,
) : AuthService {
    override suspend fun refreshTokens(refreshToken: String): DataResult<Tokens, SessionError> =
        try {
            val response: HttpResponse =
                httpClient
                    .post("auth/refresh_tokens") {
                        cookie("refresh_token", refreshToken)
                    }

            when (response.status) {
                HttpStatusCode.OK -> DataResult.Success(response.body<AuthResponse>().toDomain())
                HttpStatusCode.Unauthorized -> DataResult.Error(SessionError.SessionExpired)
                else -> DataResult.Error(SessionError.UnknownError)
            }
        } catch (e: IOException) {
            DataResult.Error(e.toSessionError())
        }

    override suspend fun login(
        email: String,
        password: String,
    ): DataResult<Tokens, AuthError> {
        return try {
            val response: HttpResponse =
                httpClient
                    .post("auth/login") {
                        setBody(LoginRequest(email = email, password = password))
                    }

            return when (response.status) {
                HttpStatusCode.OK -> DataResult.Success(response.body<AuthResponse>().toDomain())
                HttpStatusCode.Unauthorized -> DataResult.Error(AuthError.Unauthorized)
                HttpStatusCode.NotFound -> DataResult.Error(AuthError.UserNotFound)
                else -> DataResult.Error(AuthError.UnknownError)
            }
        } catch (e: IOException) {
            DataResult.Error(e.toAuthError())
        }
    }

    override suspend fun signInWithGoogle(idToken: String): DataResult<Tokens, AuthError> {
        return try {
            val response: HttpResponse =
                httpClient.post("auth/login/google/mobile") {
                    setBody(GoogleIdTokenRequest(idToken))
                }

            return when (response.status) {
                HttpStatusCode.OK -> DataResult.Success(response.body<AuthResponse>().toDomain())
                HttpStatusCode.Unauthorized -> DataResult.Error(AuthError.Unauthorized)
                HttpStatusCode.NotFound -> DataResult.Error(AuthError.UserNotFound)
                else -> DataResult.Error(AuthError.UnknownError)
            }
        } catch (e: IOException) {
            DataResult.Error(e.toAuthError())
        }
    }
}
