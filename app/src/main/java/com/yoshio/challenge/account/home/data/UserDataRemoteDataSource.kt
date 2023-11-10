package com.yoshio.challenge.account.home.data

import com.google.firebase.firestore.FirebaseFirestore
import com.yoshio.challenge.account.home.domain.UserInfo
import com.yoshio.challenge.account.home.domain.toUserData
import com.yoshio.core.exceptions.ApiExceptionHandler
import com.yoshio.core.exceptions.ApiRequestException.UserNotFoundException
import com.yoshio.core.flow.Result
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class UserDataRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore,
                                                   private val apiExceptionHandler: ApiExceptionHandler) {

    suspend fun getUserInfo(uid: String): Result<UserInfo> {
        return try {
            val documentSnapshot = firestore.collection("users").document(uid).get().await()

            if (documentSnapshot.exists()) {
                Result.Success(documentSnapshot.toUserData())
            } else {
                Result.Error(UserNotFoundException())
            }
        } catch (exception: Exception) {
            Result.Error(apiExceptionHandler.cause(exception))
        }
    }
}
