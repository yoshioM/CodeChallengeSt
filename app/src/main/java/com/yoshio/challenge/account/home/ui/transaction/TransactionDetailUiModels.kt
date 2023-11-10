package com.yoshio.challenge.account.home.ui.transaction

import com.yoshio.challenge.account.home.ui.TransactionUiModel

sealed class TransactionAction {
    data class ShowTransactionInfo(val transactionUiModel: TransactionUiModel) : TransactionAction()
}
