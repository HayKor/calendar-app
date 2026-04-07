package com.haykor.calendar.feature.auth.presentation

data class SplashScreenState(
    val isAuthorized: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null,
)

sealed interface SplashScreenIntent {
    data object CheckAuth : SplashScreenIntent
}

sealed interface SplashScreenEvent {
    data object NavigateToMain : SplashScreenEvent

    data object NavigateToAuth : SplashScreenEvent
}
