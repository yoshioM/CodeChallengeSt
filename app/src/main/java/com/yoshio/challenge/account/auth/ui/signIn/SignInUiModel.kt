package com.yoshio.challenge.account.auth.ui.signIn

data class SignInUiModel(val showProgress: Boolean,
                         val isLoginSuccess: Boolean,
                         val exception: Exception?)

sealed class SignInActions {
    object OpenHome : SignInActions()
    object OpenSignUp : SignInActions()
}
