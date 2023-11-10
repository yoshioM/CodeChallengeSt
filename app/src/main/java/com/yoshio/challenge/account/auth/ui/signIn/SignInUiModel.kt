package com.yoshio.challenge.account.auth.ui.signIn

data class SignInUiModel(val showProgress: Boolean,
                         val isLoginSuccess: Boolean,
                         val userId: String,
                         val exception: Exception?)

sealed class SignInActions {
    data class OpenHome(val userId: String) : SignInActions()
    object OpenSignUp : SignInActions()
}
