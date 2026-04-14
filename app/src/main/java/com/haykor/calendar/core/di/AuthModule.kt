package com.haykor.calendar.core.di

import com.haykor.calendar.feature.auth.data.service.AuthServiceImpl
import com.haykor.calendar.feature.auth.domain.service.AuthService
import com.haykor.calendar.feature.auth.domain.usecase.LoginUseCase
import com.haykor.calendar.feature.auth.presentation.login.LoginViewModel
import com.haykor.calendar.feature.splash.presentation.splash.SplashScreenViewModel
import org.koin.core.module.dsl.new
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule =
    module {
        single { new(::LoginUseCase) }

        single<AuthService> { new(::AuthServiceImpl) }

        viewModel { new(::SplashScreenViewModel) }
        viewModel { new(::LoginViewModel) }
    }
