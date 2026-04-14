package com.haykor.calendar.feature.splash.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haykor.calendar.core.common.domain.DataResult
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.auth.domain.usecase.EnsureAuthenticatedUseCase
import com.haykor.calendar.feature.auth.presentation.mapper.toUiText
import com.haykor.calendar.feature.splash.domain.usecase.CheckForInternetConnectionUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val ensureAuthenticatedUseCase: EnsureAuthenticatedUseCase,
    private val checkForInternetConnectionUseCase: CheckForInternetConnectionUseCase,
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
            when (val connStatus = checkForInternetConnectionUseCase()) {
                is DataResult.Success -> {}

                is DataResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = connStatus.error.toUiText(),
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
                        AuthError.SessionExpired,
                        AuthError.Unauthorized,
                        AuthError.UserNotFound,
                        -> {
                            _state.update { it.copy(isLoading = false) }
                            _event.send(SplashScreenEvent.NavigateToAuth)
                        }

                        AuthError.NetworkError,
                        AuthError.UnknownError,
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
