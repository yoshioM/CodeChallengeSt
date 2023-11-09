package com.yoshio.challenge.splash

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View.ALPHA
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yoshio.challenge.account.auth.ui.AuthActivity
import com.yoshio.challenge.account.auth.ui.AuthEntryPoint
import com.yoshio.challenge.databinding.ActivitySplashBinding
import com.yoshio.challenge.splash.SplashActions.HOME
import com.yoshio.challenge.splash.SplashActions.SIGN_IN
import com.yoshio.challenge.splash.di.initDagger
import com.yoshio.core.di.viewmodel.ViewModelProviderFactory
import com.yoshio.styling.OnAnimatorListener
import com.yoshio.styling.extension.liveDataObserve
import com.yoshio.styling.extension.viewBinding
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory<SplashViewModel>

    private val splashViewModel by viewModels<SplashViewModel> { viewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initDagger()
        initObservers()
        startOnLoginOrHome()
    }

    private fun initObservers() {
        liveDataObserve(splashViewModel.splashActions) { openSplashAction(it ?: return@liveDataObserve) }
    }

    private fun startOnLoginOrHome() {
        ObjectAnimator.ofFloat(binding.logoImageView, ALPHA, ANIMATION_ALPHA_START, ANIMATION_ALPHA_END).apply {
            duration = ANIMATION_DURATION
            addListener(OnAnimatorListener { splashViewModel.validateLogin() })
            start()
        }
    }

    private fun openSplashAction(splashAction: SplashActions) {
        when (splashAction) {
            SIGN_IN -> navigateTo(AuthActivity.createClearIntent(this, AuthEntryPoint.SIGN_IN))
            HOME -> Unit
        }
    }

    private fun navigateTo(intent: Intent?) {
        startActivity(intent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    companion object {

        private const val ANIMATION_DURATION = 1200L
        private const val ANIMATION_ALPHA_START = 0.0f
        private const val ANIMATION_ALPHA_END = 1.0f
    }
}
