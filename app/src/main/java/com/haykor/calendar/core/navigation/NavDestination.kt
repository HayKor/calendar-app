package com.haykor.calendar.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

interface NavDestination : NavKey

@Composable
fun <T : NavKey> rememberNavBackStack(vararg elements: T): NavBackStack<T> = NavBackStack(*elements)
