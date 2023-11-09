package com.yoshio.core.exceptions

import com.yoshio.core.R

sealed class ApiRequestException(val messageError: Int) : Exception() {
    class Authentication : ApiRequestException(R.string.error_auth)
    class AuthenticationInvalidUser : ApiRequestException(R.string.error_auth_invalid_user)
    class AuthenticationInvalidCredentials : ApiRequestException(R.string.error_auth_invalid_credentials)
    class Unknown : ApiRequestException(R.string.error_unknown)
}
