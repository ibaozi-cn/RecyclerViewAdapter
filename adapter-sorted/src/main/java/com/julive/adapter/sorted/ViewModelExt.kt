package com.julive.adapter.sorted

import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.ViewModel


typealias SortedItemVMType = ViewModel<out SortedModel,out RecyclerView.ViewHolder,SortedListAdapter>

abstract class SortedItemViewModel<M : SortedModel, VH : RecyclerView.ViewHolder> :
    ViewModel<M, VH, SortedListAdapter>()