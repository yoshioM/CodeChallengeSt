package com.yoshio.challenge.account.home.ui.transaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yoshio.challenge.R
import com.yoshio.challenge.account.home.ui.TransactionUiModel
import com.yoshio.challenge.account.home.ui.transaction.TransactionAction.ShowTransactionInfo
import com.yoshio.challenge.account.home.ui.transaction.TransactionDetailActivity.Companion.TRANSACTION_AMOUNT_EXTRA
import com.yoshio.challenge.account.home.ui.transaction.TransactionDetailActivity.Companion.TRANSACTION_DATE_EXTRA
import com.yoshio.challenge.account.home.ui.transaction.TransactionDetailActivity.Companion.TRANSACTION_RECEIVER_EXTRA
import com.yoshio.challenge.account.home.ui.transaction.TransactionDetailActivity.Companion.TRANSACTION_SENDER_EXTRA
import com.yoshio.challenge.account.home.ui.transaction.TransactionDetailActivity.Companion.TRANSACTION_TYPE_EXTRA
import com.yoshio.challenge.databinding.FragmentTransactionDetailBinding
import com.yoshio.styling.extension.getActivityIntent
import com.yoshio.styling.extension.getString
import com.yoshio.styling.extension.liveEventObserve
import com.yoshio.styling.extension.setOnToolbarBackPressed
import com.yoshio.styling.extension.viewBinding

class TransactionDetailFragment : Fragment(R.layout.fragment_transaction_detail) {

    private val binding by viewBinding { FragmentTransactionDetailBinding.bind(requireView()) }

    private val transactionDetailViewModel by viewModels<TransactionDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initSupportActionBar()
        transactionDetailViewModel.showTransactionInfo(getTransactionUiModel())
    }

    private fun initObservers() {
        liveEventObserve(transactionDetailViewModel.transactionActions) { transactionActions(it) }
    }

    private fun initSupportActionBar() {
        setOnToolbarBackPressed(binding.transactionToolbar)
    }

    private fun transactionActions(transactionAction: TransactionAction) = transactionAction.run {
        when (this) {
            is ShowTransactionInfo -> showTransactionInfo(transactionUiModel)
        }
    }

    private fun showTransactionInfo(transactionUiModel: TransactionUiModel) = transactionUiModel.run {
        binding.typeTextView.text = getString(R.string.type_transaction, type)
        binding.dateTextView.text = getString(R.string.date_transaction, date)
        binding.senderTextView.text = getString(R.string.sender_transaction, sender)
        binding.receiverTextView.text = getString(R.string.receiver_transaction, receiver)
        binding.amountTextView.text = getString(R.string.amount_transaction, amount)
    }

    private fun getTransactionUiModel() = TransactionUiModel(
            type = getActivityIntent().getString(TRANSACTION_TYPE_EXTRA),
            date = getActivityIntent().getString(TRANSACTION_DATE_EXTRA),
            sender = getActivityIntent().getString(TRANSACTION_SENDER_EXTRA),
            receiver = getActivityIntent().getString(TRANSACTION_RECEIVER_EXTRA),
            amount = getActivityIntent().getString(TRANSACTION_AMOUNT_EXTRA)
    )

    companion object {

        fun newInstance() = TransactionDetailFragment()
    }
}
