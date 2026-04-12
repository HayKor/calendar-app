package com.haykor.calendar.core.navigation

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.haykor.calendar.feature.auth.presentation.login.LoginScreen
import com.haykor.calendar.feature.auth.presentation.login.LoginScreenDestination
import com.haykor.calendar.feature.auth.presentation.onboarding.OnboardingDestination
import com.haykor.calendar.feature.auth.presentation.onboarding.OnboardingScreen
import com.haykor.calendar.feature.auth.presentation.splash.SplashDestination
import com.haykor.calendar.feature.auth.presentation.splash.SplashScreen

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
                    onNavigateToMain = {
                        rootBackstack.removeLastOrNull()
                        rootBackstack.add(
                            OnboardingDestination,
                        )
                    }, // TODO: navigate
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
                animationSpec = tween(450, delayMillis = 30, easing = EaseOutCubic),
            ) { it } togetherWith
                scaleOut(
                    animationSpec = tween(450, easing = EaseOutCubic),
                    targetScale = 0.92f,
                ) + fadeOut(tween(450, easing = EaseOutCubic))
        },
        popTransitionSpec = {
            scaleIn(
                animationSpec = tween(450, easing = EaseOutCubic),
                initialScale = 0.92f,
            ) + fadeIn(tween(450, easing = EaseOutCubic)) togetherWith
                slideOutHorizontally(
                    animationSpec = tween(450, easing = EaseOutCubic),
                ) { it }
        },
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    )
}
