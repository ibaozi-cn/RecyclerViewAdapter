package com.julive.adapter.paging


import androidx.recyclerview.widget.DiffUtil.ItemCallback

class DiffViewModelCallBack : ItemCallback<ItemPageViewModel<*>>() {

    override fun areItemsTheSame(
        oldItem: ItemPageViewModel<*>,
        newItem: ItemPageViewModel<*>
    ): Boolean {
        return oldItem.model?.isSameModelAs(newItem.model)?:false
    }

    override fun areContentsTheSame(
        oldItem: ItemPageViewModel<*>,
        newItem: ItemPageViewModel<*>
    ): Boolean {
        return oldItem.model?.isContentTheSameAs(newItem.model) ?: false
    }

}