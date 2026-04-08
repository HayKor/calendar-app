package com.haykor.calendar.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haykor.calendar.R
import com.haykor.calendar.core.common.domain.DataResult
import com.haykor.calendar.core.common.presentation.UiText
import com.haykor.calendar.feature.auth.domain.AuthError
import com.haykor.calendar.feature.auth.domain.EnsureAuthenticatedUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val ensureAuthorizedUseCase: EnsureAuthenticatedUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SplashScreenState())
    val state: StateFlow<SplashScreenState> = _state.asStateFlow()

    private val _event = Channel<SplashScreenEvent>()
    val event = _event.receiveAsFlow()

    init {
        onIntent(SplashScreenIntent.CheckAuth)
    }

    fun onIntent(intent: SplashScreenIntent) {
        when (intent) {
            is SplashScreenIntent.CheckAuth -> checkAuth()
        }
    }

    private fun checkAuth() {
        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = ensureAuthorizedUseCase()) {
                is DataResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.error.toUiText(),
                        )
                    }
                }

                is DataResult.Success -> {
                    _state.update {
                        it.copy(
                            isAuthorized = result.data,
                            isLoading = false,
                        )
                    }
                    if (result.data) {
                        _event.send(SplashScreenEvent.NavigateToMain)
                    } else {
                        _event.send(SplashScreenEvent.NavigateToAuth)
                    }
                }
            }
        }
    }

    private fun AuthError.toUiText(): UiText =
        when (this) {
            AuthError.NetworkError -> UiText.StringResource(R.string.error_network)
            AuthError.UnknownError -> UiText.StringResource(R.string.error_unknown)
        }
}
