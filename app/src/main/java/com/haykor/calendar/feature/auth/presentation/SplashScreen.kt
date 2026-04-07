package com.haykor.calendar.feature.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.haykor.calendar.core.ui.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashScreenViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is SplashScreenEvent.NavigateToMain -> {}
                is SplashScreenEvent.NavigateToAuth -> {}
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painterResource(R.drawable.baseline_calendar_month_24),
            contentDescription =
                stringResource(
                    R.string.app_icon_description,
                ),
            modifier = Modifier.size(150.dp),
        )
        ContainedLoadingIndicator(
            indicatorColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(100.dp),
        )
    }
}

@PreviewLightDark
@Composable
private fun SplashScreenPreview() {
    AppTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
        ) { paddingValues ->
            SplashScreen(
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}
