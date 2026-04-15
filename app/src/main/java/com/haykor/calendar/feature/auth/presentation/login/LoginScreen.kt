package com.haykor.calendar.feature.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haykor.calendar.R
import com.haykor.calendar.core.common.presentation.component.AppButton
import com.haykor.calendar.core.common.presentation.component.AppIcon
import com.haykor.calendar.core.common.presentation.component.AppOutlinedSecretTextField
import com.haykor.calendar.core.common.presentation.component.AppOutlinedTextField
import com.haykor.calendar.core.ui.theme.AppTheme
import com.haykor.calendar.core.ui.theme.LocalSpacing
import org.koin.androidx.compose.koinViewModel

private object LoginScreenDimensions {
    val appLogoSize = 24.dp
}

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignup: () -> Unit,
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
    val context = LocalContext.current

    state.error?.let {
        Toast.makeText(context, it.asString(), Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        LoginScreen(
            state = state,
            onIntent = { intent ->
                when (intent) {
                    is LoginScreenIntent.NavigateToSignup -> onNavigateToSignup()
                    else -> viewModel.onIntent(intent)
                }
            },
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onIntent: (LoginScreenIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = spacing.medium),
    ) {
        HeaderSection()
        Spacer(Modifier.height(spacing.large))
        FormSection(emailState = state.email, passwordState = state.password)
        Spacer(Modifier.height(spacing.extraMedium))
        LoginOptionsSection(
            onTryLogin = { onIntent(LoginScreenIntent.TryLogin) },
        )
        Spacer(Modifier.weight(1f))
        SignUpPrompt(
            onSignUpClick = { onIntent(LoginScreenIntent.NavigateToSignup) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = spacing.medium),
        )
    }
}

@Composable
private fun HeaderSection(modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(top = spacing.medium),
        verticalArrangement = Arrangement.spacedBy(spacing.large, Alignment.CenterVertically),
        horizontalAlignment = Alignment.Start,
    ) {
        AppTitleWithIcon()
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing.medium, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(R.string.sign_in_to_your_account),
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                text = stringResource(R.string.enter_your_email_and_password_to_log_in),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun FormSection(
    emailState: TextFieldState,
    passwordState: TextFieldState,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.medium),
    ) {
        AppOutlinedTextField(
            state = emailState,
            label = "Email",
            modifier = Modifier.fillMaxWidth(),
        )
        AppOutlinedSecretTextField(
            state = passwordState,
            label = "Password",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun AppTitleWithIcon(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppIcon(
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(LoginScreenDimensions.appLogoSize),
        )
        Text(
            text = stringResource(R.string.display_app_name),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun LoginOptionsSection(
    onTryLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.extraMedium),
    ) {
        AppButton(onClick = onTryLogin, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Log in",
            )
        }
        DividerWithText(text = "or")
        AppButton(
            onClick = {},
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.small),
            ) {
                Icon(
                    painterResource(R.drawable.google_24dp),
                    contentDescription = "Google logo",
                    tint = Color.Unspecified,
                )
                Text(
                    text = "Continue with Google",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
private fun DividerWithText(
    text: String,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.small),
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        HorizontalDivider(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun SignUpPrompt(
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val prompt = "Don't have an account?"
    val signUp = stringResource(R.string.sign_up)

    val annotatedText =
        buildAnnotatedString {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                append("$prompt ")
            }
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(signUp)
            }
        }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = annotatedText,
            style = MaterialTheme.typography.bodyMedium,
            modifier =
                Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onSignUpClick,
                ),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        Scaffold { paddingValues ->
            LoginScreen(
                state = LoginState(),
                onIntent = {},
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}
