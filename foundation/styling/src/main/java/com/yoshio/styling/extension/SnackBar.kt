package com.yoshio.styling.extension

import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.yoshio.styling.R

fun Fragment.snackbar(text: CharSequence?, duration: Int = LENGTH_SHORT) =
        Snackbar.make(requireView(), text ?: "", duration).apply { show() }

fun Fragment.snackbar(@StringRes resId: Int, duration: Int = LENGTH_SHORT) =
        Snackbar.make(requireView(), resId, duration).apply { show() }

fun Snackbar.showSuccess() = apply { view.setBackgroundResource(R.color.success_snackbar) }

fun Snackbar.showWarning() = apply { view.setBackgroundResource(R.color.warning_snackbar) }

fun Snackbar.showError() = apply { view.setBackgroundResource(R.color.error_snackbar) }
