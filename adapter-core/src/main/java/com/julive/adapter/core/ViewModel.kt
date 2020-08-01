package com.julive.adapter.core

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

interface ViewModel<M, VH : RecyclerView.ViewHolder> :
    ViewHolderFactory<VH> {
    var model: M?
    val itemViewType: Int
        get() = layoutRes
    @get:LayoutRes
    val layoutRes: Int
}