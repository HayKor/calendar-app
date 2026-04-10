package com.haykor.calendar.feature.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haykor.calendar.R
import com.haykor.calendar.core.common.presentation.AppButton
import com.haykor.calendar.core.ui.theme.AppTheme
import com.haykor.calendar.core.ui.theme.LocalSpacing

private object OnboardingScreenDimensions {
    val LogoSize = 150.dp
}

@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToSignup: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(spacing.medium),
        verticalArrangement = Arrangement.spacedBy(spacing.large, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppIcon(
            modifier = Modifier.size(OnboardingScreenDimensions.LogoSize),
        )
        ButtonsSection(
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToSignup = onNavigateToSignup,
        )
    }
}

@Composable
fun ButtonsSection(
    onNavigateToLogin: () -> Unit,
    onNavigateToSignup: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppButton(onClick = onNavigateToLogin, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.log_in),
            )
        }
        AppButton(onClick = onNavigateToSignup, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.sign_up),
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    AppTheme {
        Scaffold { paddingValues ->
            OnboardingScreen(
                onNavigateToLogin = {},
                onNavigateToSignup = {},
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}
