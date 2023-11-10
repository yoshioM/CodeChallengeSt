package com.yoshio.styling.widget

import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerAdapter<ITEM, HOLDER : RecyclerView.ViewHolder> : RecyclerView.Adapter<HOLDER>() {

    private val itemList: MutableList<ITEM> = mutableListOf()
    protected open var realFirstItemPosition = FIRST_ITEM_POSITION

    open fun set(items: List<ITEM>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun add(item: ITEM, position: Int = itemCount - 1) {
        itemList.add(position, item)
        notifyItemInserted(position + realFirstItemPosition)
    }

    fun addAll(items: List<ITEM>) {
        itemList.addAll(items)
        notifyItemRangeInserted(realFirstItemPosition + itemCount - items.size, items.size)
    }

    fun replace(item: ITEM, position: Int) {
        itemList[position] = item
        notifyItemChanged(position + realFirstItemPosition)
    }

    fun remove(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position + realFirstItemPosition)
    }

    fun item(position: Int): ITEM? = if (position < itemList.size) itemList[position] else null

    fun items(): List<ITEM> = itemList

    fun clear() {
        val size = size()
        itemList.clear()
        notifyItemRangeRemoved(realFirstItemPosition, size)
    }

    fun size() = itemList.size

    fun isEmpty() = items().isEmpty()

    override fun getItemCount() = size()

    companion object {
        const val FIRST_ITEM_POSITION = 0
        const val UNINITIALIZED_INDEX = -1
        const val NOT_FOUND = -1
    }
}
