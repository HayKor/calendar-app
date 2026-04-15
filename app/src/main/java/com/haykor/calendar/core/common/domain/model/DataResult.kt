package com.haykor.calendar.core.common.domain.model

interface RootError

sealed interface DataResult<out D, out E : RootError> {
    data class Success<out D>(
        val data: D,
    ) : DataResult<D, Nothing>

    data class Error<out E : RootError>(
        val error: E,
    ) : DataResult<Nothing, E>
}

inline fun <D, E : RootError, R> DataResult<D, E>.map(transform: (D) -> R): DataResult<R, E> =
    when (this) {
        is DataResult.Success -> DataResult.Success(transform(data))
        is DataResult.Error -> DataResult.Error(error)
    }

inline fun <D, E : RootError, R> DataResult<D, E>.flatMap(transform: (D) -> DataResult<R, E>): DataResult<R, E> =
    when (this) {
        is DataResult.Success -> transform(data)
        is DataResult.Error -> DataResult.Error(error)
    }
