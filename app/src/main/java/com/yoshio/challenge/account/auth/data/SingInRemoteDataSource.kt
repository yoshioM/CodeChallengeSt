package com.yoshio.challenge.account.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.yoshio.challenge.account.auth.domain.User
import com.yoshio.core.exceptions.ApiExceptionHandler
import com.yoshio.core.exceptions.ApiRequestException.Authentication
import com.yoshio.core.flow.Result
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class SingInRemoteDataSource @Inject constructor(private val firebaseAuth: FirebaseAuth,
                                                 private val apiExceptionHandler: ApiExceptionHandler) {

    suspend fun loginWithEmailAndPassword(email: String, password: String): Result<User> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                Result.Success(result.toUser())
            } else {
                Result.Error(Authentication())
            }
        } catch (exception: Exception) {
            Result.Error(apiExceptionHandler.cause(exception))
        }
    }
}
