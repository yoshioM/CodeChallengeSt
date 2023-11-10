package com.yoshio.styling.extension

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import com.yoshio.styling.R
import com.yoshio.styling.listeners.SingleClickListener

private const val NO_FLAGS = 0

fun View.hideOrShow(visible: Boolean) {
    visibility = if (visible) VISIBLE else GONE
}

fun View.visible() {
    visibility = VISIBLE
}

fun View.setOnSingleClickListener(onSingleClickListener: ((View) -> Unit)?) {
    setOnClickListener(SingleClickListener { onSingleClickListener?.invoke(it) })
}

fun View.dismissKeyboard() = getInputMethodManager().hideSoftInputFromWindow(this@dismissKeyboard.windowToken, NO_FLAGS)

private fun View.getInputMethodManager() = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun View.color(@ColorRes colorRes: Int) = context.color(colorRes)

fun View.setStrokeContainer(@ColorRes colorRes: Int, @DimenRes cornerRadius: Int) =
        setStrokeRipple(color = color(colorRes), cornerRadius = resources.getDimension(cornerRadius))

fun View.setStrokeRipple(color: Int, hasDotted: Boolean = false, fillColor: Int = Color.TRANSPARENT,
                         cornerRadius: Float = context.resources.getDimension(R.dimen.corner_radius),
                         strokeWidth: Float = context.resources.getDimension(R.dimen.divider_height)) =
        try {
            background = context.drawable(R.drawable.shape_text_grey_outline)?.mutate()
            ((background as RippleDrawable).getDrawable(0) as GradientDrawable).apply {
                val dashWidth = context.resources.getDimension(R.dimen.dash_width)
                val dashGap = context.resources.getDimension(R.dimen.dash_gap)
                this.cornerRadius = cornerRadius
                setColor(fillColor)
                if (hasDotted) setStroke(strokeWidth.toInt(), color, dashWidth, dashGap)
                else setStroke(strokeWidth.toInt(), color)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
