package com.haykor.calendar.core.di

import com.haykor.calendar.feature.auth.domain.usecase.EnsureAuthenticatedUseCase
import com.haykor.calendar.feature.auth.domain.usecase.GetAuthStatusUseCase
import com.haykor.calendar.feature.auth.domain.usecase.RefreshTokenUseCase
import com.haykor.calendar.feature.splash.data.NetworkMonitor
import com.haykor.calendar.feature.splash.domain.usecase.CheckForInternetConnectionUseCase
import org.koin.core.module.dsl.new
import org.koin.dsl.module

val splashModule =
    module {
        single { new(::GetAuthStatusUseCase) }
        single { new(::RefreshTokenUseCase) }
        single { new(::EnsureAuthenticatedUseCase) }
        single { new(::CheckForInternetConnectionUseCase) }

        single { new(::NetworkMonitor) }
    }
