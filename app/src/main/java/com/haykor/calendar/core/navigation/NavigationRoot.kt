package com.haykor.calendar.core.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.haykor.calendar.feature.auth.navigation.OnboardingDestination
import com.haykor.calendar.feature.auth.navigation.SplashDestination
import com.haykor.calendar.feature.auth.presentation.OnboardingScreen
import com.haykor.calendar.feature.auth.presentation.SplashScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val rootBackstack = rememberNavBackStack<NavDestination>(SplashDestination)

    val entryProvider =
        entryProvider {
            entry<SplashDestination> {
                SplashScreen(
                    onNavigateToOnboarding = { rootBackstack.add(OnboardingDestination) },
                    onNavigateToMain = { }, // TODO: navigate
                )
            }
            entry<OnboardingDestination> {
                OnboardingScreen(
                    onNavigateToLogin = {},
                    onNavigateToSignup = {},
                )
            }
        }

    NavDisplay(
        entryProvider = entryProvider,
        backStack = rootBackstack,
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
        transitionSpec = {
            slideInHorizontally { it } + fadeIn() togetherWith
                slideOutHorizontally { -it } + fadeOut()
        },
        popTransitionSpec = {
            slideInHorizontally { -it } + fadeIn() togetherWith
                slideOutHorizontally { it } + fadeOut()
        },
        modifier = modifier.fillMaxSize(),
    )
}
