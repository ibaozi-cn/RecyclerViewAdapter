package com.julive.adapter.sorted

import com.julive.adapter.core.ArrayListAdapter
import com.julive.adapter.core.DefaultViewHolder
import com.julive.adapter.core.ViewModel

abstract class SortedListViewModel<M : SortedModel>() :
    ViewModel<M, DefaultViewHolder, ArrayListAdapter>()