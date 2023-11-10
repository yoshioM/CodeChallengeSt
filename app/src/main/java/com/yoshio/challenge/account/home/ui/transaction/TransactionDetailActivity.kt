package com.yoshio.challenge.account.home.ui.transaction

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.core.os.bundleOf
import com.yoshio.challenge.R
import com.yoshio.challenge.account.home.ui.TransactionUiModel
import com.yoshio.styling.extension.intentTo
import com.yoshio.styling.views.CompatBindingFragActivity

class TransactionDetailActivity : CompatBindingFragActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
    }

    override fun containerId() = R.id.fragment_container

    override fun fragment() = TransactionDetailFragment.newInstance()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return false
    }

    companion object {
        const val TRANSACTION_TYPE_EXTRA = "transaction_type"
        const val TRANSACTION_DATE_EXTRA = "transaction_date"
        const val TRANSACTION_SENDER_EXTRA = "transaction_sender"
        const val TRANSACTION_RECEIVER_EXTRA = "transaction_receiver"
        const val TRANSACTION_AMOUNT_EXTRA = "transaction_amount"

        fun createIntent(context: Context, transactionUiModel: TransactionUiModel) = transactionUiModel.run {
            context.intentTo<TransactionDetailActivity>().putExtras(bundleOf(
                    TRANSACTION_TYPE_EXTRA to type,
                    TRANSACTION_DATE_EXTRA to date,
                    TRANSACTION_SENDER_EXTRA to sender,
                    TRANSACTION_RECEIVER_EXTRA to receiver,
                    TRANSACTION_AMOUNT_EXTRA to amount))
        }
    }
}
