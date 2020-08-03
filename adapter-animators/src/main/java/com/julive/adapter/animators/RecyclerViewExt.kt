package com.julive.adapter.animators

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.Adapter<*>.intoWithAnimator(
    recyclerView: RecyclerView,
    layoutManager: RecyclerView.LayoutManager? = null,
    itemAnimator: BaseItemAnimator = DefaultItemAnimator()
) = apply {
    recyclerView.layoutManager = layoutManager ?: LinearLayoutManager(recyclerView.context)
    recyclerView.itemAnimator = itemAnimator
    recyclerView.adapter = this
}