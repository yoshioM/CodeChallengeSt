package com.yoshio.challenge.account.auth.domain

import com.yoshio.challenge.account.auth.data.FirebaseRepository
import com.yoshio.challenge.account.auth.ui.signUp.SignUpData
import com.yoshio.core.flow.Result
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {

    suspend fun signUp(userData: SignUpData): Result<Unit> {
        return firebaseRepository.signUp(userData)
    }
}
