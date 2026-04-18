package com.haykor.calendar.core.di

import com.haykor.calendar.core.session.domain.service.TokensRefreshService
import com.haykor.calendar.core.session.domain.usecase.EnsureAuthenticatedUseCase
import com.haykor.calendar.core.session.domain.usecase.GetAuthStatusUseCase
import com.haykor.calendar.core.session.domain.usecase.RefreshTokenUseCase
import com.haykor.calendar.feature.auth.domain.service.AuthService
import com.haykor.calendar.feature.splash.data.service.AndroidNetworkMonitor
import com.haykor.calendar.feature.splash.data.service.HealthCheckServiceImpl
import com.haykor.calendar.feature.splash.domain.service.HealthCheckService
import com.haykor.calendar.feature.splash.domain.service.NetworkMonitor
import com.haykor.calendar.feature.splash.domain.usecase.CheckServiceAccessibilityUseCase
import com.haykor.calendar.feature.splash.presentation.splash.SplashScreenViewModel
import org.koin.core.module.dsl.new
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val splashModule =
    module {
        single { new(::GetAuthStatusUseCase) }
        single { new(::RefreshTokenUseCase) }
        single { new(::EnsureAuthenticatedUseCase) }
        single { new(::CheckServiceAccessibilityUseCase) }

        single<NetworkMonitor> { new(::AndroidNetworkMonitor) }
        single<HealthCheckService> { new(::HealthCheckServiceImpl) }
        single<TokensRefreshService> { get<AuthService>() }

        viewModel { new(::SplashScreenViewModel) }
    }
