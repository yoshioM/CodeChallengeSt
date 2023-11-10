package com.yoshio.challenge.account.auth.data

import java.util.Calendar
import kotlin.random.Random

data class TransactionInfoApiRequest(
        val type: String,
        val amount: Double,
        val date: String,
        val sender: String,
        val receiver: String)


fun generateRandomTransactions(count: Int): List<TransactionInfoApiRequest> {
    val transactionTypes = listOf("Compra", "Depósito", "Transferencia")
    val senders = listOf("Tienda", "Trabajo", "Usuario")
    val receivers = listOf("Usuario", "Amigo")

    return List(count) {
        val type = transactionTypes[Random.nextInt(transactionTypes.size)]
        val amount:Double = Double.getRandom()
        val date = generateRandomDate()
        val sender = senders[Random.nextInt(senders.size)]
        val receiver = receivers[Random.nextInt(receivers.size)]

        TransactionInfoApiRequest(type, amount, date, sender, receiver)
    }
}

private fun generateRandomDate(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -Random.nextInt(CALENDAR_DAYS))
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return "$year-$month-$day"
}


fun Double.Companion.getRandom() = Random.nextDouble(INITIAL_RANGE_RANDOM, FINAL_RANGE_RANDOM)

const val INITIAL_RANGE_RANDOM = 100.0
const val FINAL_RANGE_RANDOM = 5000.0
const val CALENDAR_DAYS = 365
