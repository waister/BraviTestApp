package br.com.waister.bravitestapp.utils

sealed class ViewState<out T> {
    class Success<T>(val value: T) : ViewState<T>()
    class Error(val error: Throwable) : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
}
