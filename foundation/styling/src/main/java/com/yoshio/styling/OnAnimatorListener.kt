package com.yoshio.styling

import android.animation.Animator

class OnAnimatorListener(private val onAnimationEnd: () -> Unit) : Animator.AnimatorListener {

    override fun onAnimationRepeat(p0: Animator) {}

    override fun onAnimationEnd(p0: Animator) {
        onAnimationEnd.invoke()
    }

    override fun onAnimationCancel(p0: Animator) {}

    override fun onAnimationStart(p0: Animator) {}

}
