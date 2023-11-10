package com.yoshio.challenge.account.home.ui

import com.yoshio.challenge.account.home.domain.TransactionInfo
import com.yoshio.challenge.account.home.domain.UserInfo
import com.yoshio.styling.extension.toStringTwoDecimals
import java.util.Locale

data class UserDataUiModel(val showProgress: Boolean,
                           val userInfoUi: UserInfoUi?,
                           val exception: Exception?)

sealed class UserDataActions {
    object OpenDetailTransaction : UserDataActions()
}

data class UserInfoUi(
        val name: String,
        val lastName: String,
        val balanceAmount: String,
        val transactionList: List<TransactionUi>)

data class TransactionUi(
        val type: String,
        val amount: String,
        val date: String,
        val sender: String,
        val receiver: String)

fun UserInfo.toUserInfoUi() = UserInfoUi(
        name = name,
        lastName = lastName,
        balanceAmount = balance.toStringTwoDecimals(Locale.getDefault()),
        transactionList = transactions.toTransactionListUi())

fun List<TransactionInfo>.toTransactionListUi() = mapNotNull { it.toTransactionUi() }

fun TransactionInfo.toTransactionUi() = TransactionUi(
        type = type,
        amount = amount.toStringTwoDecimals(Locale.getDefault()),
        date = date,
        sender = sender,
        receiver = receiver)
