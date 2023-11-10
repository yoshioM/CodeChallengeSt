package com.yoshio.challenge.account.auth.data

import com.yoshio.challenge.account.auth.ui.signUp.SignUpData
import com.yoshio.core.flow.Result
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val signInRemoteDataSource: FirebaseRemoteDataSource) {

    suspend fun login(email: String, password: String) =
            signInRemoteDataSource.loginWithEmailAndPassword(email = email, password = password)

    suspend fun signUp(userData: SignUpData): Result<Unit> =
        signInRemoteDataSource.registerWithEmailAndPassword(userData)

}
