package com.haykor.calendar.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.haykor.calendar.feature.auth.navigation.SplashDestination
import com.haykor.calendar.feature.auth.navigation.splashEntryProvider

@Composable
fun RootScreen(modifier: Modifier = Modifier) {
    val backstack = rememberNavBackStack(SplashDestination)

    NavDisplay(
        entryProvider =
            entryProvider {
                splashEntryProvider(backstack)
            },
        backStack = backstack,
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
    )
}
