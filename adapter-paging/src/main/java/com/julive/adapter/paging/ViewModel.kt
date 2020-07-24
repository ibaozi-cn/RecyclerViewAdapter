package com.julive.adapter.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julive.adapter.core.DefaultViewModel
import com.julive.adapter.core.SameModel

typealias  PagingItemViewModelType = PagingItemViewModel<out SameModel>

class PagingItemViewModel<M : SameModel> : DefaultViewModel<M, PagingListAdapter>() {
    override var layoutRes: Int = 0
    override fun getHolderItemView(parent: ViewGroup, layoutInflater: LayoutInflater): View {
        return layoutInflater.inflate(layoutRes, parent, false)
    }
}