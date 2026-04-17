package com.haykor.calendar.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.auth.domain.model.GoogleSignInResult
import com.haykor.calendar.feature.auth.domain.service.GoogleSignInClient
import com.haykor.calendar.feature.auth.domain.usecase.GoogleLoginUseCase
import com.haykor.calendar.feature.auth.domain.usecase.LoginUseCase
import com.haykor.calendar.feature.auth.presentation.mapper.toUiText
import com.haykor.calendar.feature.auth.presentation.validation.EmailValidator
import com.haykor.calendar.feature.auth.presentation.validation.PasswordValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val googleSignInClient: GoogleSignInClient,
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val emailValidator: EmailValidator,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _event = Channel<LoginScreenEvent>()
    val event = _event.receiveAsFlow()

    fun onIntent(intent: LoginScreenIntent) {
        when (intent) {
            LoginScreenIntent.TryLogin -> {
                tryLogin()
            }

            is LoginScreenIntent.TryGoogleSignIn -> {
                tryGoogleSignIn()
            }

            else -> {}
        }
    }

    private fun tryLogin() =
        viewModelScope.launch {
            val emailError =
                emailValidator(
                    _state.value.email.text
                        .toString(),
                )
            val passwordError =
                PasswordValidator(
                    _state.value.password.text
                        .toString(),
                )

            _state.update { it.copy(emailError = emailError, passwordError = passwordError) }

            if (emailError != null || passwordError != null) return@launch

            performLogin()
        }

    private fun tryGoogleSignIn() =
        viewModelScope.launch {
            _state.update { it.copy(isLoading = false, error = null) }
            when (val signInResult = googleSignInClient.signIn()) {
                is GoogleSignInResult.Success -> {
                    when (val result = googleLoginUseCase(signInResult.idToken)) {
                        is DataResult.Error -> handleLoginError(result.error)
                        is DataResult.Success -> handleLoginSuccess()
                    }
                }

                GoogleSignInResult.Failure,
                GoogleSignInResult.Cancelled,
                -> {
                    _state.update { it.copy(isLoading = false, error = signInResult.toUiText()) }
                }
            }
        }

    private suspend fun performLogin() {
        _state.update { it.copy(isLoading = true, error = null) }

        val result =
            loginUseCase(
                email =
                    _state.value.email.text
                        .toString(),
                password =
                    _state.value.password.text
                        .toString(),
            )

        when (result) {
            is DataResult.Error -> handleLoginError(result.error)
            is DataResult.Success -> handleLoginSuccess()
        }
    }

    private fun handleLoginError(error: AuthError) {
        _state.update { it.copy(isLoading = false, error = error.toUiText()) }
    }

    private suspend fun handleLoginSuccess() {
        _state.update { it.copy(isLoading = false, error = null) }
        _event.send(LoginScreenEvent.NavigateToMain)
    }
}
