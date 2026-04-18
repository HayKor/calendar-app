package com.haykor.calendar.feature.splash.domain.service

interface HealthCheckService {
    suspend fun checkHealth(): Boolean
}
