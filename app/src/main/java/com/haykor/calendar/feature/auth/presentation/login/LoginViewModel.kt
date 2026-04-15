package com.haykor.calendar.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.feature.auth.domain.model.AuthError
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
