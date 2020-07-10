package com.julive.adapter.paging

import com.julive.adapter.core.DefaultItemViewModel
import com.julive.adapter.core.SameModel

typealias  PagingItemViewModelType = PagingItemViewModel<out PageModel>

class PagingItemViewModel<M : PageModel> : DefaultItemViewModel<M, PagingListAdapter>() {
    override var layoutRes: Int = 0
}

interface PageModel : SameModel