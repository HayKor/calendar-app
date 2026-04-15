package com.haykor.calendar.feature.auth

import com.haykor.calendar.core.navigation.NavDestination

sealed interface AuthDestination : NavDestination {
    data object Onboarding : AuthDestination

    data object Login : AuthDestination

    data object Signup : AuthDestination
}
