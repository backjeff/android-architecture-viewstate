package com.backjeff.android.architecture.viewstate.extensions

import com.backjeff.android.architecture.viewstate.ViewState
import com.backjeff.android.architecture.viewstate.ViewState.Error
import com.backjeff.android.architecture.viewstate.ViewState.Loading
import com.backjeff.android.architecture.viewstate.ViewState.Neutral
import com.backjeff.android.architecture.viewstate.ViewState.Success
import com.backjeff.android.architecture.viewstate.ViewState.Transitional
import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<ViewState<T>>.postNeutral() {
    value = Transitional
    value = Neutral
}

fun <T> MutableStateFlow<ViewState<T>>.postSuccess(data: T) {
    value = Transitional
    value = Success(data)
}

fun <T> MutableStateFlow<ViewState<T>>.postError(error: Throwable) {
    value = Transitional
    value = Error(error)
}

fun <T> MutableStateFlow<ViewState<T>>.postError(message: String?) {
    value = Transitional
    value = Error(Throwable(message))
}

fun <T> MutableStateFlow<ViewState<T>>.postLoading() {
    value = Loading
}
