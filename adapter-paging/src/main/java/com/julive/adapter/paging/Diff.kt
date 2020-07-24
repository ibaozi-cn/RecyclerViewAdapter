package com.julive.adapter.paging


import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.julive.adapter.core.SameModel
import com.julive.adapter.core.ViewModelType

class DiffViewModelCallBack : ItemCallback<ViewModelType>() {
    override fun areItemsTheSame(
        oldItem: ViewModelType,
        newItem: ViewModelType
    ): Boolean {
        return (oldItem.model as? SameModel)?.isSameModelAs(newItem.model as SameModel) ?: false
    }
    override fun areContentsTheSame(
        oldItem: ViewModelType,
        newItem: ViewModelType
    ): Boolean {
        return (oldItem.model as? SameModel)?.isContentTheSameAs(newItem.model as SameModel) ?: false
    }

    override fun getChangePayload(oldItem: ViewModelType, newItem: ViewModelType): Any? {
        return (oldItem.model as? SameModel)?.getChangePayload(oldItem.model as SameModel)
    }
}