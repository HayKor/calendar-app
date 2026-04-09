package com.haykor.calendar.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.haykor.calendar.feature.auth.navigation.SplashDestination
import com.haykor.calendar.feature.auth.navigation.splashEntryProvider

@Composable
fun RootScreen() {
    val backstack = rememberNavBackStack<NavDestination>(SplashDestination)

    NavDisplay(
        entryProvider =
            entryProvider {
                splashEntryProvider(
                    onNavigateToAuth = { },
                    onNavigateToMain = { }, // TODO:
                )
            },
        backStack = backstack,
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
        modifier = Modifier.fillMaxSize(),
    )
}
