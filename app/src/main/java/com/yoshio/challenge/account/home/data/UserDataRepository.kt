package com.yoshio.challenge.account.home.data

import javax.inject.Inject

class UserDataRepository @Inject constructor(private val userDataRemoteDataSource: UserDataRemoteDataSource) {

    suspend fun getUserInfo(userId: String) =
            userDataRemoteDataSource.getUserInfo(userId)
}
