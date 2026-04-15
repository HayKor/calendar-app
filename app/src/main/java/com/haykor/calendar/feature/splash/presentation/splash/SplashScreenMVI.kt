package com.haykor.calendar.feature.splash.presentation.splash

import androidx.compose.runtime.Stable
import com.haykor.calendar.core.common.presentation.model.UiText

@Stable
data class SplashScreenState(
    val isAuthorized: Boolean = false,
    val isLoading: Boolean = true,
    val error: UiText? = null,
)

sealed interface SplashScreenIntent {
    data object CheckAuth : SplashScreenIntent
}

sealed interface SplashScreenEvent {
    data object NavigateToMain : SplashScreenEvent

    data object NavigateToAuth : SplashScreenEvent
}
