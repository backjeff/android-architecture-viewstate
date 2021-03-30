package com.backjeff.android.architecture.viewstate

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

interface ViewStateListener {

    fun onViewStateError(error: Throwable) { /* ignored */ }
    fun onStartLoading() { /* ignored */ }
    fun onFinishLoading() { /* ignored */ }

    private fun <T> ViewState<T>.handle(
        onError: ((Throwable) -> Unit)? = null,
        onLoading: (() -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
        onNeutral: (() -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null,
    ) {

        stateHandler(
            onError = { error ->
                onFinishLoading()
                onError?.invoke(error) ?: onViewStateError(error)
                onComplete?.invoke()
            },
            onLoading = { onLoading?.invoke() ?: onStartLoading() },
            onNeutral = {
                onFinishLoading()
                onNeutral?.invoke()
            },
            onSuccess = {
                onFinishLoading()
                onSuccess?.invoke(it)
                onComplete?.invoke()
            },
        )
    }

    suspend fun <T> StateFlow<ViewState<T>>.onPostValue(
        lifecycleOwner: LifecycleOwner,
        onError: ((Throwable) -> Unit)? = null,
        onLoading: (() -> Unit)? = null,
        onNeutral: (() -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null
    ) {
        collect { viewState ->
            viewState.handle(
                onError = onError,
                onLoading = onLoading,
                onNeutral = onNeutral,
                onComplete = onComplete,
                onSuccess = onSuccess
            )
        }
    }

    suspend fun <T> LiveData<ViewState<T>>.onPostValue(
        lifecycleOwner: LifecycleOwner,
        onError: ((Throwable) -> Unit)? = null,
        onLoading: (() -> Unit)? = null,
        onNeutral: (() -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null
    ) {
        observe(lifecycleOwner) { viewState ->
            viewState.handle(
                onError = onError,
                onLoading = onLoading,
                onNeutral = onNeutral,
                onComplete = onComplete,
                onSuccess = onSuccess
            )
        }
    }
}
