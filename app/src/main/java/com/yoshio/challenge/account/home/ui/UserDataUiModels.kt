package com.yoshio.challenge.account.home.ui

import com.yoshio.challenge.account.home.domain.TransactionInfo
import com.yoshio.challenge.account.home.domain.UserInfo
import com.yoshio.styling.extension.toStringTwoDecimals
import java.util.Locale

data class UserDataUiModel(val showProgress: Boolean,
                           val userInfoUiModel: UserInfoUiModel?,
                           val exception: Exception?)

sealed class UserDataActions {
    object OpenDetailTransaction : UserDataActions()
}

data class UserInfoUiModel(
        val name: String,
        val lastName: String,
        val balanceAmount: String,
        val transactionList: List<TransactionUiModel>)

data class TransactionUiModel(
        val type: String,
        val amount: String,
        val date: String,
        val sender: String,
        val receiver: String)

fun UserInfo.toUserInfoUi() = UserInfoUiModel(
        name = name,
        lastName = lastName,
        balanceAmount = balance.toStringTwoDecimals(Locale.getDefault()),
        transactionList = transactions.toTransactionListUi())

fun List<TransactionInfo>.toTransactionListUi() = mapNotNull { it.toTransactionUi() }

fun TransactionInfo.toTransactionUi() = TransactionUiModel(
        type = type,
        amount = amount.toStringTwoDecimals(Locale.getDefault()),
        date = date,
        sender = sender,
        receiver = receiver)
