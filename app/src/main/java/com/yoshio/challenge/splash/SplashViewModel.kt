package com.yoshio.challenge.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoshio.challenge.splash.SplashActions.SIGN_IN
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel() {

    private val mutableSplashAction = MutableLiveData<SplashActions>()

    val splashActions: LiveData<SplashActions>
        get() = mutableSplashAction

    fun validateLogin() {
        mutableSplashAction.value = SIGN_IN
    }
}
