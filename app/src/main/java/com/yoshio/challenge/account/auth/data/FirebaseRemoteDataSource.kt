package com.yoshio.challenge.account.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.yoshio.challenge.account.auth.domain.User
import com.yoshio.challenge.account.auth.ui.signUp.SignUpData
import com.yoshio.core.exceptions.ApiExceptionHandler
import com.yoshio.core.exceptions.ApiRequestException.Authentication
import com.yoshio.core.flow.Result
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class FirebaseRemoteDataSource @Inject constructor(private val firebaseAuth: FirebaseAuth,
                                                   private val firestore: FirebaseFirestore,
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

    suspend fun registerWithEmailAndPassword(userData: SignUpData): Result<Unit> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password).await()

            val user = result.user
            if (user != null) {
                updateUserDetails(user.uid, userData)
                Result.Success(Unit)
            } else {
                Result.Error(Authentication())
            }
        } catch (exception: Exception) {
            Result.Error(apiExceptionHandler.cause(exception))

        }
    }

    private suspend fun updateUserDetails(uid: String, userData: SignUpData): Result<Unit> {
        return try {
            val batch = firestore.batch()

            val userMap = mapOf(
                    FIRST_NAME_FIELD to userData.name,
                    LAST_NAME_FIELD to userData.lastName
            )

            val userRef = firestore.collection(COLLECTION_USERS).document(uid)
            batch.set(userRef, userMap, SetOptions.merge())

            val dummyBalance = Double.getRandom()
            batch.update(userRef, BALANCE_FIELD, dummyBalance)

            val dummyTransactions = generateRandomTransactions(COUNT_DUMMY_DATA)

            val transactionsMap = hashMapOf(
                    COLLECTION_TRANSACTIONS to dummyTransactions.map { transaction ->
                        hashMapOf(
                                TYPE_FIELD to transaction.type,
                                AMOUNT_FIELD to transaction.amount,
                                DATE_FIELD to transaction.date,
                                SENDER_FIELD to transaction.sender,
                                RECEIVER_FIELD to transaction.receiver
                        )
                    }
            )
            userRef.set(transactionsMap, SetOptions.merge())

            batch.commit().await()

            Result.Success(Unit)
        } catch (exception: Exception) {
            Result.Error(apiExceptionHandler.cause(exception))
        }
    }

    companion object {
        const val FIRST_NAME_FIELD = "firstName"
        const val LAST_NAME_FIELD = "lastName"
        private const val COLLECTION_USERS = "users"

        private const val COUNT_DUMMY_DATA = 5
        const val BALANCE_FIELD = "balance"
        const val COLLECTION_TRANSACTIONS = "transactions"

        const val TYPE_FIELD = "type"
        const val AMOUNT_FIELD = "amount"
        const val DATE_FIELD = "date"
        const val SENDER_FIELD = "sender"
        const val RECEIVER_FIELD = "receiver"
    }

}
