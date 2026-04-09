package com.haykor.calendar.feature.auth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.haykor.calendar.feature.auth.presentation.SplashScreen

@Composable
fun EntryProviderScope<NavKey>.splashEntryProvider(backStack: NavBackStack<NavKey>) {
    entry<SplashDestination> {
        SplashScreen(
            navigateToAuth = {}, // TODO:
            navigateToMain = {},
        )
    }
}
