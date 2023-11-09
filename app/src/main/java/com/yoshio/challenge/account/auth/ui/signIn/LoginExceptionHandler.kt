package com.yoshio.challenge.account.auth.ui.signIn

import androidx.annotation.StringRes
import com.yoshio.challenge.R
import com.yoshio.challenge.account.auth.ui.signIn.LoginException.EmailEmpty
import com.yoshio.challenge.account.auth.ui.signIn.LoginException.EmailInvalid
import com.yoshio.challenge.account.auth.ui.signIn.LoginException.PasswordEmpty
import com.yoshio.styling.extension.isValidEmail
import javax.inject.Inject

class LoginExceptionHandler @Inject constructor() {

    fun areInvalidCredentials(email: String, password: String): Pair<Boolean, Exception> = when {
        email.isBlank() -> Pair(true, EmailEmpty())
        !email.isValidEmail() -> Pair(true, EmailInvalid())
        password.isBlank() -> Pair(true, PasswordEmpty())
        else -> Pair(false, NoValidationNeeded)
    }
}

sealed class LoginException : Exception() {
    class EmailEmpty(@StringRes val error: Int = R.string.error_empty_email) : LoginException()
    class EmailInvalid(@StringRes val error: Int = R.string.error_invalid_email) : LoginException()
    class PasswordEmpty(@StringRes val error: Int = R.string.error_empty_password) : LoginException()
}

object NoValidationNeeded : LoginException()
