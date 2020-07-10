package com.julive.adapter.paging


import androidx.recyclerview.widget.DiffUtil.ItemCallback

class DiffViewModelCallBack : ItemCallback<PagingItemViewModelType>() {

    override fun areItemsTheSame(
        oldItem: PagingItemViewModelType,
        newItem: PagingItemViewModelType
    ): Boolean {
        return oldItem.model?.isSameModelAs(newItem.model)?:false
    }

    override fun areContentsTheSame(
        oldItem: PagingItemViewModelType,
        newItem: PagingItemViewModelType
    ): Boolean {
        return oldItem.model?.isContentTheSameAs(newItem.model)?:false
    }

}