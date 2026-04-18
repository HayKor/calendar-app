package com.haykor.calendar.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import com.haykor.calendar.feature.auth.AuthDestination
import com.haykor.calendar.feature.auth.presentation.login.LoginScreen
import com.haykor.calendar.feature.auth.presentation.onboarding.OnboardingScreen
import com.haykor.calendar.feature.splash.presentation.splash.SplashDestination
import com.haykor.calendar.feature.splash.presentation.splash.SplashScreen

@Composable
internal fun rememberRootEntryProvider(backStack: NavBackStack<NavDestination>) =
    entryProvider {
        splashEntry(backStack)
        onboardingEntry(backStack)
        loginEntry()
    }

private fun EntryProviderScope<NavDestination>.splashEntry(backStack: NavBackStack<NavDestination>) {
    entry<SplashDestination> {
        SplashScreen(
            onNavigateToOnboarding = {
                backStack.removeLastOrNull()
                backStack.add(AuthDestination.Onboarding)
            },
            onNavigateToMain = {
                backStack.removeLastOrNull()
                backStack.add(AuthDestination.Onboarding) // TODO: navigate to main
            },
        )
    }
}

private fun EntryProviderScope<NavDestination>.onboardingEntry(backStack: NavBackStack<NavDestination>) {
    entry<AuthDestination.Onboarding> {
        OnboardingScreen(
            onNavigateToLogin = {
                backStack.removeLastOrNull()
                backStack.add(AuthDestination.Login)
            },
            onNavigateToSignup = {},
        )
    }
}

private fun EntryProviderScope<NavDestination>.loginEntry() {
    entry<AuthDestination.Login> {
        LoginScreen(
            onLoginSuccess = {}, // TODO
            onNavigateToSignup = {},
        )
    }
}
