package com.yoshio.challenge.account.auth.data

import com.google.firebase.auth.AuthResult
import com.yoshio.challenge.account.auth.domain.User

fun AuthResult.toUser() = User(
        userId = user?.uid.orEmpty(),
        email = user?.email.orEmpty())
