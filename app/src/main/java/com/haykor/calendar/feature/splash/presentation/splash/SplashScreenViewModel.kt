package com.haykor.calendar.feature.splash.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.core.common.domain.model.SessionError
import com.haykor.calendar.core.session.domain.usecase.EnsureAuthenticatedUseCase
import com.haykor.calendar.core.session.presentation.mapper.toUiText
import com.haykor.calendar.feature.splash.domain.usecase.CheckServiceAccessibilityUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val ensureAuthenticatedUseCase: EnsureAuthenticatedUseCase,
    private val checkServiceAccessibilityUseCase: CheckServiceAccessibilityUseCase,
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
            when (checkServiceAccessibilityUseCase()) {
                true -> {}

                false -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = SessionError.NetworkError.toUiText(),
                        )
                    }
                    return@launch
                }
            }

            when (val result = ensureAuthenticatedUseCase()) {
                is DataResult.Success -> {
                    _state.update { it.copy(isLoading = false, isAuthorized = true) }
                    _event.send(SplashScreenEvent.NavigateToMain)
                }

                is DataResult.Error -> {
                    when (result.error) {
                        SessionError.SessionExpired,
                        -> {
                            _state.update { it.copy(isLoading = false) }
                            _event.send(SplashScreenEvent.NavigateToAuth)
                        }

                        SessionError.NetworkError,
                        SessionError.UnknownError,
                        -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.error.toUiText(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
