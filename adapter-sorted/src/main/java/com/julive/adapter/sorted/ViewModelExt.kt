package com.julive.adapter.sorted

import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.ViewModel

abstract class SortedItemViewModel<M : SortedModel, VH : RecyclerView.ViewHolder> :
    ViewModel<M, VH, SortedListAdapter>()