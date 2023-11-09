package com.yoshio.challenge.account.auth.ui

data class SignInUiModel(val showProgress: Boolean,
                         val isLoginSuccess: Boolean,
                         val exception: Exception?)

sealed class SignInActions {
    object OpenHome : SignInActions()
}
