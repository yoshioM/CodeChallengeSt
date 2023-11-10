package com.yoshio.core.exceptions

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.yoshio.core.exceptions.ApiRequestException.AuthUserCollision
import com.yoshio.core.exceptions.ApiRequestException.Authentication
import com.yoshio.core.exceptions.ApiRequestException.AuthenticationInvalidCredentials
import com.yoshio.core.exceptions.ApiRequestException.AuthenticationInvalidUser
import com.yoshio.core.exceptions.ApiRequestException.Unknown

class ApiExceptionHandler {
    fun cause(throwable: Throwable) = when (throwable) {
        is FirebaseAuthInvalidUserException -> AuthenticationInvalidUser()
        is FirebaseAuthInvalidCredentialsException -> AuthenticationInvalidCredentials()
        is FirebaseAuthException -> Authentication()
        is FirebaseAuthUserCollisionException -> AuthUserCollision()
        else -> Unknown()
    }
}
