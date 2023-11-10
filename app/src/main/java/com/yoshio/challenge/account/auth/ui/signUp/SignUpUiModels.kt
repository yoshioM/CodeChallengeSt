package com.yoshio.challenge.account.auth.ui.signUp

data class SignUpUiModel(val showProgress: Boolean,
                         val isIdRequired:Boolean,
                         val isRegisterSuccess: Boolean,
                         val exception: Exception?)

sealed class SignUpActions {
    object OpenTakeId : SignUpActions()
    object RequestCameraPermission : SignUpActions()
    object OpenSuccessSignUp : SignUpActions()
    object OpenLogin: SignUpActions()
}

data class SignUpData(
        val name:String,
        val lastName:String,
        val email:String,
        val password:String,
        val confirmPassword:String)
