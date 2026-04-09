package com.haykor.calendar.feature.auth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import com.haykor.calendar.core.navigation.NavDestination
import com.haykor.calendar.feature.auth.presentation.SplashScreen

@Composable
fun EntryProviderScope<NavDestination>.splashEntryProvider(
    onNavigateToAuth: () -> Unit,
    onNavigateToMain: () -> Unit,
) {
    entry<SplashDestination> {
        SplashScreen(
            onNavigateToAuth = onNavigateToAuth,
            onNavigateToMain = onNavigateToMain,
        )
    }
}
