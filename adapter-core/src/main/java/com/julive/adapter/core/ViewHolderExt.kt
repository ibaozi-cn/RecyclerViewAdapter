package com.julive.adapter.core

import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter_core.R

fun <Adapter : IAdapter<*>> RecyclerView.ViewHolder.getAdapter(): Adapter? {
    return this.itemView.getTag(R.id.adapter) as? Adapter
}

fun <VM : ViewModelType> RecyclerView.ViewHolder.getViewModel(): VM? {
    return this.itemView.getTag(R.id.adapter_item) as? VM
}

fun <M> RecyclerView.ViewHolder.getModel(): M? {
    return (this.itemView.getTag(R.id.adapter_item) as? ViewModelType)?.model as? M
}
