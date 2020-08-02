package com.julive.adapter.binding

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.ViewHolder.getBinding(): ViewDataBinding {
    return itemView.getTag(R.id.list_adapter_binding) as ViewDataBinding
}