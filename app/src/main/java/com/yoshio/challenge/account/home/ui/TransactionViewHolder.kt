package com.yoshio.challenge.account.home.ui

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.yoshio.challenge.R
import com.yoshio.challenge.databinding.ItemTransactionBinding
import com.yoshio.styling.extension.setOnSingleClickListener
import com.yoshio.styling.extension.setStrokeContainer
import com.yoshio.styling.R as stylingR

class TransactionViewHolder(private val binding: ItemTransactionBinding,
                            private val onTransactionListener: ((TransactionUiModel) -> Unit)?) : ViewHolder(binding.root) {

    fun bind(transactionUiModel: TransactionUiModel) = transactionUiModel.run {
        binding.root.setStrokeContainer(colorRes = stylingR.color.colorOnSecondary, cornerRadius = stylingR.dimen.space_12)
        binding.typeTextView.text = type
        binding.dateTextView.text = date
        binding.receiverTextView.text = receiver
        binding.amountTextView.text = itemView.context.getString(R.string.amount,amount)

        binding.root.setOnSingleClickListener { onTransactionListener?.invoke(this) }
    }
}
