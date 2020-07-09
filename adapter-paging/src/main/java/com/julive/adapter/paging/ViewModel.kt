package com.julive.adapter.paging

import com.julive.adapter.core.DefaultItemViewModel

class ItemPageViewModel<M : PageModel> : DefaultItemViewModel<M, PagingListAdapter>(){
    override var layoutRes: Int = 0
}