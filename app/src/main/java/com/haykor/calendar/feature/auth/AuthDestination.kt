package com.haykor.calendar.feature.auth

import com.haykor.calendar.core.navigation.NavDestination
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthDestination : NavDestination {
    @Serializable
    data object Onboarding : AuthDestination

    @Serializable
    data object Login : AuthDestination

    @Serializable
    data object Signup : AuthDestination
}
