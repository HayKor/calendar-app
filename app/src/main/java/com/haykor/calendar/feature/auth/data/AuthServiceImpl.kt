package com.haykor.calendar.feature.auth.data

import com.haykor.calendar.core.common.domain.DataResult
import com.haykor.calendar.core.common.domain.Tokens
import com.haykor.calendar.feature.auth.data.mapper.toDomain
import com.haykor.calendar.feature.auth.domain.AuthError
import com.haykor.calendar.feature.auth.domain.AuthService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.cookie
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

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
            HttpStatusCode.OK -> DataResult.Success(response.body<AuthResponse>().toDomain())
            HttpStatusCode.Unauthorized -> DataResult.Error(AuthError.Unauthorized)
            else -> DataResult.Error(AuthError.UnknownError)
        }
    }
}
