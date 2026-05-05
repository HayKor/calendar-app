package com.haykor.calendar.feature.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haykor.calendar.core.common.presentation.model.UiText
import com.haykor.calendar.feature.auth.domain.service.GoogleSignInClient
import com.haykor.calendar.feature.auth.domain.usecase.SignupUseCase
import com.haykor.calendar.feature.auth.presentation.login.LoginScreenEvent
import com.haykor.calendar.feature.auth.presentation.login.LoginScreenIntent
import com.haykor.calendar.feature.auth.presentation.validation.EmailValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel (
    private val signupUseCase: SignupUseCase,
    private val googleSignInClient: GoogleSignInClient,
    private val emailValidator: EmailValidator,
) : ViewModel() {
    private val _state = MutableStateFlow(SignupState())

    val state = _state.asStateFlow()

    private val _event = Channel<SignupScreenEvent>()

    val event = _event.receiveAsFlow()

    fun onIntent(intent: SignupScreenIntent){
        when (intent) {
            SignupScreenIntent.TrySignup -> {
                trySignup()
            }

            SignupScreenIntent.TryGoogleSignUp -> {
                tryGoogleSignUp()
            }

            else -> {}
        }
    }

    //TODO Написать функции для попытки регистрации и через гугл и для регистрации
    private fun trySignup() = Unit


    private fun tryGoogleSignUp() = Unit


    private suspend fun performSignup() {
        // дописать
    }

    private fun handleError(message: UiText) {
        _state.update { it.copy(isLoading = false) }
        viewModelScope.launch { _event.send(SignupScreenEvent.ShowError(message)) }
    }

    private suspend fun handleSuccess() {
        _state.update { it.copy(isLoading = false) }
        _event.send(SignupScreenEvent.NavigateToMain)
    }
}