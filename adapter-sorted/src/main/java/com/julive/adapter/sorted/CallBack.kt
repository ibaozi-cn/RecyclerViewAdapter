package com.julive.adapter.sorted

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.julive.adapter.core.DefaultViewModel
import com.julive.adapter.core.SameModel
import com.julive.adapter.core.ViewModelType

class SortedCallBack(adapter: RecyclerView.Adapter<*>) : SortedListAdapterCallback<ViewModelType>(adapter) {

    override fun areItemsTheSame(
        item1: ViewModelType,
        item2: ViewModelType
    ): Boolean {
        return (item1.model as? SortedModel)?.isSameModelAs(item2.model as SortedModel) ?: false
    }

    override fun compare(o1: ViewModelType, o2: ViewModelType): Int {
        return (o1.model as? SortedModel)?.compare(o2.model as SortedModel) ?: 0
    }

    override fun areContentsTheSame(
        oldItem: ViewModelType,
        newItem: ViewModelType
    ): Boolean {
        return (oldItem.model as? SortedModel)?.isContentTheSameAs(newItem.model as SameModel)
            ?: false
    }

    override fun getChangePayload(item1: ViewModelType?, item2: ViewModelType?): Any? {
        return (item1?.model as? SortedModel)?.getChangePayload(item2?.model as SortedModel)
    }

}