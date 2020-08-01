package com.julive.adapter.core

import androidx.recyclerview.widget.RecyclerView

interface Subscriber<VH : RecyclerView.ViewHolder> {
    fun onBindViewHolder(viewHolder: VH, position: Int, payloads: List<Any>)
    fun unBindViewHolder(viewHolder: VH, position: Int)
    fun onViewAttachedToWindow(viewHolder: VH, position: Int) {}
    fun onViewDetachedFromWindow(viewHolder: VH, position: Int) {}
    fun onFailedToRecycleView(viewHolder: VH, position: Int): Boolean = false
}
