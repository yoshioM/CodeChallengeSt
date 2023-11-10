package com.yoshio.challenge.account.home.domain

import com.google.firebase.firestore.DocumentSnapshot
import com.yoshio.challenge.account.auth.data.FirebaseRemoteDataSource.Companion.AMOUNT_FIELD
import com.yoshio.challenge.account.auth.data.FirebaseRemoteDataSource.Companion.BALANCE_FIELD
import com.yoshio.challenge.account.auth.data.FirebaseRemoteDataSource.Companion.COLLECTION_TRANSACTIONS
import com.yoshio.challenge.account.auth.data.FirebaseRemoteDataSource.Companion.DATE_FIELD
import com.yoshio.challenge.account.auth.data.FirebaseRemoteDataSource.Companion.FIRST_NAME_FIELD
import com.yoshio.challenge.account.auth.data.FirebaseRemoteDataSource.Companion.LAST_NAME_FIELD
import com.yoshio.challenge.account.auth.data.FirebaseRemoteDataSource.Companion.RECEIVER_FIELD
import com.yoshio.challenge.account.auth.data.FirebaseRemoteDataSource.Companion.SENDER_FIELD
import com.yoshio.challenge.account.auth.data.FirebaseRemoteDataSource.Companion.TYPE_FIELD
import com.yoshio.styling.extension.default
import com.yoshio.styling.extension.orDefault

data class UserInfo(
        val name: String,
        val lastName: String,
        val balance: Double,
        val transactions: List<TransactionInfo>
)

data class TransactionInfo(
        val type: String,
        val amount: Double,
        val date: String,
        val sender: String,
        val receiver: String)

fun DocumentSnapshot.toUserData(): UserInfo {
    val name = getString(FIRST_NAME_FIELD).orEmpty()
    val lastName = getString(LAST_NAME_FIELD).orEmpty()
    val balance = getDouble(BALANCE_FIELD).orDefault()
    val transactions = toTransactionList()

    return UserInfo(name, lastName, balance, transactions)
}

fun DocumentSnapshot.toTransactionList(): List<TransactionInfo> {
    val transactions = get(COLLECTION_TRANSACTIONS) as? List<HashMap<String, Any>> ?: emptyList()

    return transactions.mapNotNull { transactionMap ->
        val type = transactionMap[TYPE_FIELD] as? String ?: String.empty()
        val amount = transactionMap[AMOUNT_FIELD] as? Double ?: Double.default()
        val date = transactionMap[DATE_FIELD] as? String ?: String.empty()
        val sender = transactionMap[SENDER_FIELD] as? String ?: String.empty()
        val receiver = transactionMap[RECEIVER_FIELD] as? String ?: String.empty()

        TransactionInfo(type, amount, date, sender, receiver)
    }
}

fun String.Companion.empty() = ""
