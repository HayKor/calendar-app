package com.haykor.calendar.feature.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(onLoginSuccess) {
        viewModel.event.collect { event ->
            when (event) {
                LoginScreenEvent.NavigateToMain -> onLoginSuccess()
            }
        }
    }

    LoginScreen(
        state = state,
        modifier = modifier,
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    modifier: Modifier = Modifier,
) {
}
