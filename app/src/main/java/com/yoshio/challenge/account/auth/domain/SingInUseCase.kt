package com.yoshio.challenge.account.auth.domain

import com.yoshio.challenge.account.auth.data.FirebaseRepository
import com.yoshio.core.flow.Result
import javax.inject.Inject

class SingInUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {

    suspend fun login(email: String, password: String): Result<User> {
        return firebaseRepository.login(email = email, password = password)
    }
}
