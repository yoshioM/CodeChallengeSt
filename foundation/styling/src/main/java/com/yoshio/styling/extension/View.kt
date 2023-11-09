package com.yoshio.styling.extension

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import com.yoshio.styling.listeners.SingleClickListener

private const val NO_FLAGS = 0

fun View.hideOrShow(visible: Boolean) {
    visibility = if (visible) VISIBLE else GONE
}

fun View.setOnSingleClickListener(onSingleClickListener: ((View) -> Unit)?) {
    setOnClickListener(SingleClickListener { onSingleClickListener?.invoke(it) })
}

fun View.dismissKeyboard() = getInputMethodManager().hideSoftInputFromWindow(this@dismissKeyboard.windowToken, NO_FLAGS)

private fun View.getInputMethodManager() = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
