package com.haykor.calendar.feature.splash.domain.usecase

import com.haykor.calendar.core.common.domain.model.DataResult
import com.haykor.calendar.feature.auth.domain.model.AuthError
import com.haykor.calendar.feature.splash.data.NetworkMonitor

class CheckForInternetConnectionUseCase(
    private val networkMonitor: NetworkMonitor,
) {
    operator fun invoke(): DataResult<Unit, AuthError> =
        when (networkMonitor.isOnline()) {
            true -> DataResult.Success(Unit)
            false -> DataResult.Error(AuthError.NetworkError)
        }
}
