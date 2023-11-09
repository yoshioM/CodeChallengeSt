package com.yoshio.styling.extension

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.yoshio.styling.listeners.SingleClickListener

fun View.hideOrShow(visible: Boolean) {
    visibility = if (visible) VISIBLE else GONE
}

fun View.setOnSingleClickListener(onSingleClickListener: ((View) -> Unit)?) {
    setOnClickListener(SingleClickListener { onSingleClickListener?.invoke(it) })
}
