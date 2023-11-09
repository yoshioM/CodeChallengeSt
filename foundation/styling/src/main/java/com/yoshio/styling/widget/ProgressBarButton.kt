package com.yoshio.styling.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.yoshio.styling.R
import com.yoshio.styling.databinding.ProgressBarButtonBinding
import com.yoshio.styling.extension.hideOrShow
import com.yoshio.styling.extension.setOnSingleClickListener

class ProgressBarButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ProgressBarButtonBinding
    private var text = INITIAL_TEXT
    private var icon = INITIAL_ICON

    var onClickListener: (() -> Unit)? = null

    init {
        val layoutInflater = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ProgressBarButtonBinding.inflate(layoutInflater, this, true)
        applyAttributes(context, attrs)
        initProgressBarButton()
    }

    private fun initProgressBarButton() {
        binding.button.setOnSingleClickListener { onClickListener?.invoke() }
    }

    private fun applyAttributes(context: Context, attrs: AttributeSet? = null) {
        val obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarButton, 0, 0)
        try {
            text = obtainStyledAttributes.getString(R.styleable.ProgressBarButton_text).orEmpty()
            icon = obtainStyledAttributes.getResourceId(R.styleable.ProgressBarButton_icon, INITIAL_ICON)
            binding.button.text = text
            binding.button.setIconResource(icon)
        } finally {
            obtainStyledAttributes.recycle()
        }
    }

    fun showProgress(showProgress: Boolean) {
        binding.button.isClickable = !showProgress
        binding.button.text = if (showProgress) INITIAL_TEXT else text
        binding.button.setIconResource(if (showProgress) INITIAL_ICON else icon)
        binding.progressBar.hideOrShow(showProgress)
    }

    fun setText(text: String) {
        this.text = text
        binding.button.text = text
    }

    fun setText(restId: Int) {
        text = context.getString(restId)
        binding.button.text = text
    }

    fun setIcon(@DrawableRes restId: Int) {
        icon = restId
        binding.button.setIconResource(icon)
    }

    fun enableButton(enable: Boolean) {
        binding.button.isEnabled = enable
    }

    fun clear() {
        onClickListener = null
    }

    companion object {
        const val INITIAL_TEXT = ""
        const val INITIAL_ICON = 0
    }
}
