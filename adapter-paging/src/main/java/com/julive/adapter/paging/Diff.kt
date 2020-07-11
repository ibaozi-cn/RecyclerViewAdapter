package com.julive.adapter.paging


import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.julive.adapter.core.SameModel

class DiffViewModelCallBack : ItemCallback<PagingItemViewModelType>() {
    override fun areItemsTheSame(
        oldItem: PagingItemViewModelType,
        newItem: PagingItemViewModelType
    ): Boolean {
        return oldItem.model?.isSameModelAs(newItem.model as SameModel) ?: false
    }
    override fun areContentsTheSame(
        oldItem: PagingItemViewModelType,
        newItem: PagingItemViewModelType
    ): Boolean {
        return oldItem.model?.isContentTheSameAs(newItem.model as SameModel) ?: false
    }

    override fun getChangePayload(
        oldItem: PagingItemViewModelType,
        newItem: PagingItemViewModelType
    ): Any? {
        return oldItem.model?.getChangePayload(newItem.model as SameModel)
    }

}