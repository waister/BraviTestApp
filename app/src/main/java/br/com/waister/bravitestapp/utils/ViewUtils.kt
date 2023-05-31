package br.com.waister.bravitestapp.utils

import android.view.View

fun View.isVisible(isVisible: Boolean) = if (isVisible) show() else hide()

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}
