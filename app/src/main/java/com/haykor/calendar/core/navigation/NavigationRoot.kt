package com.haykor.calendar.core.navigation

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
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
import com.haykor.calendar.feature.auth.navigation.LoginScreenDestination
import com.haykor.calendar.feature.auth.navigation.OnboardingDestination
import com.haykor.calendar.feature.auth.navigation.SplashDestination
import com.haykor.calendar.feature.auth.presentation.LoginScreen
import com.haykor.calendar.feature.auth.presentation.OnboardingScreen
import com.haykor.calendar.feature.auth.presentation.SplashScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val rootBackstack = rememberNavBackStack<NavDestination>(SplashDestination)

    val entryProvider =
        entryProvider {
            entry<SplashDestination> {
                SplashScreen(
                    onNavigateToOnboarding = {
                        rootBackstack.removeLastOrNull()
                        rootBackstack.add(
                            OnboardingDestination,
                        )
                    },
                    onNavigateToMain = { }, // TODO: navigate
                )
            }
            entry<OnboardingDestination> {
                OnboardingScreen(
                    onNavigateToLogin = {
                        rootBackstack.removeLastOrNull()
                        rootBackstack.add(
                            LoginScreenDestination,
                        )
                    },
                    onNavigateToSignup = {},
                )
            }
            entry<LoginScreenDestination> {
                LoginScreen(
                    onLoginSuccess = {}, // TODO:
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
            slideInHorizontally(
                animationSpec = tween(450, easing = EaseOutCubic),
            ) { it } + fadeIn(tween(450, easing = EaseOutCubic)) togetherWith
                slideOutHorizontally(
                    animationSpec = tween(450, easing = EaseOutCubic),
                ) { -it / 3 } + fadeOut(tween(450, easing = EaseOutCubic))
        },
        popTransitionSpec = {
            slideInHorizontally(
                animationSpec = tween(450, easing = EaseOutCubic),
            ) { -it / 3 } + fadeIn(tween(450, easing = EaseOutCubic)) togetherWith
                slideOutHorizontally(
                    animationSpec = tween(450, easing = EaseOutCubic),
                ) { it } + fadeOut(tween(450, easing = EaseOutCubic))
        },
        modifier = modifier.fillMaxSize(),
    )
}
