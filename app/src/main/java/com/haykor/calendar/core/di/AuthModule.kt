package com.haykor.calendar.core.di

import com.haykor.calendar.BuildConfig
import com.haykor.calendar.feature.auth.data.service.AuthServiceImpl
import com.haykor.calendar.feature.auth.data.service.CredentialManagerGoogleSignInClient
import com.haykor.calendar.feature.auth.domain.service.AuthService
import com.haykor.calendar.feature.auth.domain.service.GoogleSignInClient
import com.haykor.calendar.feature.auth.domain.usecase.GoogleLoginUseCase
import com.haykor.calendar.feature.auth.domain.usecase.LoginUseCase
import com.haykor.calendar.feature.auth.presentation.login.LoginViewModel
import com.haykor.calendar.feature.auth.presentation.validation.AndroidEmailFormatChecker
import com.haykor.calendar.feature.auth.presentation.validation.EmailFormatChecker
import com.haykor.calendar.feature.auth.presentation.validation.EmailValidator
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.new
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule =
    module {
        single<EmailFormatChecker> { AndroidEmailFormatChecker }
        single { new(::EmailValidator) }

        single<AuthService> { new(::AuthServiceImpl) }
        single<GoogleSignInClient> {
            CredentialManagerGoogleSignInClient(
                context = androidContext(),
                webClientId = BuildConfig.GOOGLE_WEB_CLIENT_ID,
            )
        }

        single { new(::LoginUseCase) }
        single { new(::GoogleLoginUseCase) }

        viewModel { new(::LoginViewModel) }
    }
