package com.haykor.calendar.feature.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haykor.calendar.R
import com.haykor.calendar.core.common.presentation.UiText
import com.haykor.calendar.core.ui.theme.AppTheme
import com.haykor.calendar.core.ui.theme.LocalSpacing
import org.koin.compose.viewmodel.koinViewModel

private object SplashScreenDimensions {
    val LogoSize = 150.dp
    val LoadingIndicatorSize = 100.dp
}

@Composable
fun SplashScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToAuth: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashScreenViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(onNavigateToMain, onNavigateToAuth) {
        viewModel.event.collect { event ->
            when (event) {
                is SplashScreenEvent.NavigateToMain -> {
                    onNavigateToMain()
                }

                is SplashScreenEvent.NavigateToAuth -> {
                    onNavigateToAuth()
                }
            }
        }
    }

    SplashScreen(
        modifier = modifier,
        state = state,
        onIntent = viewModel::onIntent,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun SplashScreen(
    state: SplashScreenState,
    onIntent: (SplashScreenIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(spacing.large, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_calendar_month_24),
            contentDescription = stringResource(R.string.app_icon_description),
            modifier = Modifier.size(SplashScreenDimensions.LogoSize),
        )

        if (state.isLoading) {
            ContainedLoadingIndicator(
                indicatorColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(SplashScreenDimensions.LoadingIndicatorSize),
            )
        }

        state.error?.let { error ->
            Text(
                text = error.asString(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
            )
            Button(onClick = { onIntent(SplashScreenIntent.CheckAuth) }) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SplashScreenErrorPreview() {
    AppTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
        ) { paddingValues ->
            SplashScreen(
                state =
                    SplashScreenState(
                        isLoading = false,
                        error = UiText.DynamicString("Authentication failed"),
                    ),
                onIntent = {},
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SplashScreenLoadingPreview() {
    AppTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
        ) { paddingValues ->
            SplashScreen(
                state =
                    SplashScreenState(
                        isLoading = true,
                    ),
                onIntent = {},
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}
