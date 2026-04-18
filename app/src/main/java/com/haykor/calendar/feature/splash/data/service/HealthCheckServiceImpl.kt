package com.haykor.calendar.feature.splash.data.service

import com.haykor.calendar.feature.splash.domain.service.HealthCheckService
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import java.io.IOException

class HealthCheckServiceImpl(
    private val httpClient: HttpClient,
) : HealthCheckService {
    override suspend fun checkHealth(): Boolean =
        try {
            val response = httpClient.get("health")
            when (response.status) {
                HttpStatusCode.OK -> true
                else -> false
            }
        } catch (_: IOException) {
            false
        }
}
