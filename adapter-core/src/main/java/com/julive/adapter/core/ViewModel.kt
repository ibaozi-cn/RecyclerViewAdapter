package com.julive.adapter.core

import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

interface ViewModel<M, VH : RecyclerView.ViewHolder> :
    ViewHolderFactory<VH> {
    var model: M?
    var itemViewType: Int
        get() = layoutRes
        set(value) {}
    @get:LayoutRes
    val layoutRes: Int
    var isFirstInit: Boolean
    fun bindVH(
        viewHolder: VH,
        payloads: List<Any>
    ) {
    }
    fun unBindVH(viewHolder: VH) {}
    fun onDestroy(source: LifecycleOwner){}
}