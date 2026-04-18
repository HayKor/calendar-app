package com.haykor.calendar.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.haykor.calendar.feature.splash.presentation.splash.SplashDestination

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val rootBackstack = rememberNavBackStack<NavDestination>(SplashDestination)

    NavDisplay(
        backStack = rootBackstack,
        entryProvider = rememberRootEntryProvider(rootBackstack),
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
        transitionSpec = {
            forwardTransition()
        },
        popTransitionSpec = {
            backTransition()
        },
        predictivePopTransitionSpec = {
            predictiveBackTransition(it)
        },
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    )
}
