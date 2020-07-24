package com.julive.adapter.core

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.bindListAdapter(
    listAdapter: RecyclerView.Adapter<*>,
    layoutManager: RecyclerView.LayoutManager? = null
) {
    this.layoutManager = layoutManager ?: LinearLayoutManager(context)
    this.adapter = listAdapter
}

fun ViewHolderCacheAdapter<*, *>.into(
    recyclerView: RecyclerView,
    layoutManager: RecyclerView.LayoutManager? = null
) = apply {
    recyclerView.layoutManager = layoutManager ?: LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = this
}