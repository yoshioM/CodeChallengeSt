package com.yoshio.challenge.account.home.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoshio.challenge.account.home.ui.TransactionUiModel
import com.yoshio.challenge.account.home.ui.transaction.TransactionAction.ShowTransactionInfo
import com.yoshio.styling.livedata.Event

class TransactionDetailViewModel : ViewModel() {

    private val _transactionActions = MutableLiveData<Event<TransactionAction>>()

    val transactionActions: LiveData<Event<TransactionAction>>
        get() = _transactionActions

    fun showTransactionInfo(transactionUiModel: TransactionUiModel) {
        _transactionActions.value = Event(ShowTransactionInfo(transactionUiModel))
    }
}
