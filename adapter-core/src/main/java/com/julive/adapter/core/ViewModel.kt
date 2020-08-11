package com.julive.adapter.core

import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

typealias ViewModelType = ViewModel<*,*>

interface ViewModel<M, VH : RecyclerView.ViewHolder> : ViewHolderFactory<VH> {
    var model: M?
    var itemViewType: Int
        get() = layoutRes
        set(value) {}
    @get:LayoutRes
    val layoutRes: Int
    var isFirstInit: Boolean
    fun bindVH(viewHolder: VH, payloads: List<Any>) {}
    fun unBindVH(viewHolder: VH) {}
}

interface LifecycleViewModel {
    fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event){}
}