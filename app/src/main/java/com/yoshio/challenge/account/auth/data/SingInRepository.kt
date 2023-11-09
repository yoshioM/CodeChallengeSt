package com.yoshio.challenge.account.auth.data

import javax.inject.Inject

class SingInRepository @Inject constructor(private val signInRemoteDataSource: SingInRemoteDataSource) {

    suspend fun login(email: String, password: String) =
            signInRemoteDataSource.loginWithEmailAndPassword(email = email, password = password)

}
