package com.yoshio.challenge.account.auth.domain

import com.yoshio.challenge.account.auth.data.SingInRepository
import com.yoshio.core.flow.Result
import javax.inject.Inject

class SingInUseCase @Inject constructor(private val singInRepository: SingInRepository) {

    suspend fun login(email: String, password: String): Result<User> {
        return singInRepository.login(email = email, password = password)
    }
}
