package com.haykor.calendar.feature.splash.domain.usecase

import com.haykor.calendar.feature.splash.domain.service.HealthCheckService
import com.haykor.calendar.feature.splash.domain.service.NetworkMonitor

class CheckServiceAccessibilityUseCase(
    private val networkMonitor: NetworkMonitor,
    private val healthCheckService: HealthCheckService,
) {
    suspend operator fun invoke(): Boolean = networkMonitor.isOnline() && healthCheckService.checkHealth()
}
