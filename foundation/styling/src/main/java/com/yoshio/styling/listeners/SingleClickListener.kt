package com.yoshio.styling.listeners

import android.os.SystemClock
import android.view.View

class SingleClickListener(private var defaultInterval: Int = DEFAULT_INTERVAL, private val onSingleClickListener: ((View) -> Unit)?) :
        View.OnClickListener {

    private var lastTimeClicked: Long = 0

    override fun onClick(view: View) {
        val elapsedRealtime = SystemClock.elapsedRealtime()
        if (elapsedRealtime - lastTimeClicked < defaultInterval) return
        lastTimeClicked = elapsedRealtime
        onSingleClickListener?.invoke(view)
    }

    companion object {
        private const val DEFAULT_INTERVAL = 800
    }
}
