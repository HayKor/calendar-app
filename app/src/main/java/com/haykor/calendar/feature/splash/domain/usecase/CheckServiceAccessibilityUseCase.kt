package com.haykor.calendar.feature.splash.domain.usecase

import com.haykor.calendar.feature.splash.data.NetworkMonitor
import com.haykor.calendar.feature.splash.domain.service.HealthCheckService

class CheckServiceAccessibilityUseCase(
    private val networkMonitor: NetworkMonitor,
    private val healthCheckService: HealthCheckService,
) {
    suspend operator fun invoke(): Boolean = networkMonitor.isOnline() && healthCheckService.checkHealth()
}
