package com.haykor.calendar.core.di

import com.haykor.calendar.feature.auth.domain.EnsureAuthenticatedUseCase
import com.haykor.calendar.feature.auth.domain.GetAuthStatusUseCase
import com.haykor.calendar.feature.auth.domain.RefreshTokenUseCase
import com.haykor.calendar.feature.auth.presentation.SplashScreenViewModel
import org.koin.core.module.dsl.new
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule =
    module {
        single { new(::GetAuthStatusUseCase) }
        single { new(::RefreshTokenUseCase) }
        single { new(::EnsureAuthenticatedUseCase) }

        viewModel { new(::SplashScreenViewModel) }
    }
