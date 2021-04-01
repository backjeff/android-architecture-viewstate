package com.backjeff.android.architecture.viewstate

sealed class ViewState<out T> {
    object Transitional : ViewState<Nothing>()
    object Neutral : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
    data class Success<T>(val data: T) : ViewState<T>()
    data class Error(
        val t: Throwable
    ) : ViewState<Nothing>()

    fun stateHandler(
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onLoading: () -> Unit,
        onNeutral: () -> Unit,
    ) {
        when (this) {
            is Success -> onSuccess(data)
            is Error -> onError(t)
            is Loading -> onLoading()
            is Neutral -> onNeutral()
        }
    }
}
