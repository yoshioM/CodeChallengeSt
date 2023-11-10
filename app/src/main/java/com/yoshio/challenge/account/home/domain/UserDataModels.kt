package com.yoshio.challenge.account.home.domain

import com.google.firebase.firestore.DocumentSnapshot
import com.yoshio.challenge.account.auth.data.FirebaseRemoteDataSource
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

fun DocumentSnapshot.toUserData() = UserInfo(
        name = getString(FirebaseRemoteDataSource.FIRST_NAME_FIELD).orEmpty(),
        lastName = getString(FirebaseRemoteDataSource.LAST_NAME_FIELD).orEmpty(),
        balance = getDouble(FirebaseRemoteDataSource.BALANCE_FIELD).orDefault(),
        transactions = toTransactionList()

)

fun DocumentSnapshot.toTransactionList(): List<TransactionInfo> {
    val transactions = get(FirebaseRemoteDataSource.COLLECTION_TRANSACTIONS, List::class.java)

    return if (transactions is List<*>) {
        transactions.filterIsInstance<Map<*, *>>().mapNotNull { transactionMap ->
            try {
                val type = transactionMap[FirebaseRemoteDataSource.TYPE_FIELD] as? String ?: String.empty()
                val amount = transactionMap[FirebaseRemoteDataSource.AMOUNT_FIELD] as? Double ?: Double.default()
                val date = transactionMap[FirebaseRemoteDataSource.DATE_FIELD] as? String ?: String.empty()
                val sender = transactionMap[FirebaseRemoteDataSource.SENDER_FIELD] as? String ?: String.empty()
                val receiver = transactionMap[FirebaseRemoteDataSource.RECEIVER_FIELD] as? String ?: String.empty()

                TransactionInfo(type, amount, date, sender, receiver)
            } catch (e: Exception) {
                null
            }
        }
    } else {
        emptyList()
    }
}

fun String.Companion.empty() = ""
