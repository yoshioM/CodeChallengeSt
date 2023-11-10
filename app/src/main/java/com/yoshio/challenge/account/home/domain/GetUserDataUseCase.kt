package com.yoshio.challenge.account.home.domain

import com.yoshio.challenge.account.home.data.UserDataRepository
import com.yoshio.core.flow.Result
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val userDataRepository: UserDataRepository) {

    suspend fun getUserInfo(userId: String): Result<UserInfo> {
        return userDataRepository.getUserInfo(userId)
    }
}

