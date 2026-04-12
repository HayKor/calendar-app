package com.haykor.calendar.feature.auth.data.service

import androidx.compose.ui.autofill.ContentType
import com.haykor.calendar.core.common.domain.DataResult
import com.haykor.calendar.core.common.domain.Tokens
import com.haykor.calendar.feature.auth.data.mapper.toDomain
import com.haykor.calendar.feature.auth.data.model.AuthResponse
import com.haykor.calendar.feature.auth.data.model.LoginRequest
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.auth.domain.service.AuthService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.cookie
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class AuthServiceImpl(
    private val httpClient: HttpClient,
) : AuthService {
    override suspend fun refreshTokens(refreshToken: String): DataResult<Tokens, AuthError> {
        val response: HttpResponse =
            httpClient
                .post("auth/refresh_tokens") {
                    cookie("refresh_token", refreshToken)
                }

        return when (response.status) {
            HttpStatusCode.Companion.OK -> DataResult.Success(response.body<AuthResponse>().toDomain())
            HttpStatusCode.Companion.Unauthorized -> DataResult.Error(AuthError.Unauthorized)
            else -> DataResult.Error(AuthError.UnknownError)
        }
    }

    override suspend fun login(
        email: String,
        password: String,
    ): DataResult<Tokens, AuthError> {
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
    }
}
