package br.com.waister.bravitestapp.utils

sealed class ViewState<out T> {
    class Success<T>(val value: T) : ViewState<T>()
    class Error(val error: Throwable) : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
}

val <T> ViewState<T>.successValue: T?
    get() = (this as? ViewState.Success<T>)?.value
