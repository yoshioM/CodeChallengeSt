package com.yoshio.challenge.account.auth.ui.signUp

import androidx.annotation.StringRes
import com.yoshio.challenge.R
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.ConfirmPasswordEmpty
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.EmailEmpty
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.EmailInvalid
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.LastNameEmpty
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.NameEmpty
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.PasswordDontMatchEmpty
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.PasswordEmpty
import com.yoshio.styling.extension.isValidEmail
import javax.inject.Inject

class ValidatePersonalDataExceptionHandler @Inject constructor() {

    fun areInvalidData(userData: SignUpData) = userData.run {
        when {
            name.isBlank() -> Pair(true, NameEmpty())
            lastName.isBlank() -> Pair(true, LastNameEmpty())
            email.isBlank() -> Pair(true, EmailEmpty())
            !email.isValidEmail() -> Pair(true, EmailInvalid())
            password.isBlank() -> Pair(true, PasswordEmpty())
            confirmPassword.isBlank() -> Pair(true, ConfirmPasswordEmpty())
            password != confirmPassword -> Pair(true, PasswordDontMatchEmpty())
            else -> Pair(false, NoValidationNeeded)
        }
    }
}

sealed class UserDataException : Exception() {
    class NameEmpty(@StringRes val error: Int = R.string.error_empty_name) : UserDataException()
    class LastNameEmpty(@StringRes val error: Int = R.string.error_empty_last_name) : UserDataException()
    class EmailEmpty(@StringRes val error: Int = R.string.error_empty_email) : UserDataException()
    class EmailInvalid(@StringRes val error: Int = R.string.error_invalid_email) : UserDataException()
    class PasswordEmpty(@StringRes val error: Int = R.string.error_empty_password) : UserDataException()
    class ConfirmPasswordEmpty(@StringRes val error: Int = R.string.error_empty_password) : UserDataException()
    class PasswordDontMatchEmpty(@StringRes val error: Int = R.string.error_passwords_dont_match) : UserDataException()
}

object NoValidationNeeded : UserDataException()
