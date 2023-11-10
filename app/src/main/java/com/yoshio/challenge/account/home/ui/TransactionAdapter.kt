package com.yoshio.challenge.account.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yoshio.challenge.databinding.ItemTransactionBinding
import com.yoshio.styling.widget.RecyclerAdapter

class TransactionAdapter : RecyclerAdapter<TransactionUiModel, TransactionViewHolder>() {

    var onTransactionListener: ((TransactionUiModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TransactionViewHolder(binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    onTransactionListener = onTransactionListener)

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        item(position)?.run { holder.bind(this) }
    }
}
