package com.julive.adapter.paging

import com.julive.adapter.core.DefaultItemViewModel
import com.julive.adapter.core.SameModel

typealias  PagingItemViewModelType = PagingItemViewModel<out SameModel>

class PagingItemViewModel<M : SameModel> : DefaultItemViewModel<M, PagingListAdapter>() {
    override var layoutRes: Int = 0
}