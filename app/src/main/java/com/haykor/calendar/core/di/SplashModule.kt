package com.haykor.calendar.core.di

import com.haykor.calendar.feature.splash.data.NetworkMonitor
import com.haykor.calendar.feature.splash.data.service.HealthCheckServiceImpl
import com.haykor.calendar.feature.splash.domain.service.HealthCheckService
import com.haykor.calendar.feature.splash.domain.usecase.CheckServiceAccessibilityUseCase
import com.haykor.calendar.feature.splash.domain.usecase.EnsureAuthenticatedUseCase
import com.haykor.calendar.feature.splash.domain.usecase.GetAuthStatusUseCase
import com.haykor.calendar.feature.splash.domain.usecase.RefreshTokenUseCase
import org.koin.core.module.dsl.new
import org.koin.dsl.module

val splashModule =
    module {
        single { new(::GetAuthStatusUseCase) }
        single { new(::RefreshTokenUseCase) }
        single { new(::EnsureAuthenticatedUseCase) }
        single { new(::CheckServiceAccessibilityUseCase) }

        single { new(::NetworkMonitor) }
        single<HealthCheckService> { new(::HealthCheckServiceImpl) }
    }
