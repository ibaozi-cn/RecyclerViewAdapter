package com.julive.adapter.core

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.Adapter<*>.into(
    recyclerView: RecyclerView,
    layoutManager: RecyclerView.LayoutManager? = null
) = apply {
    recyclerView.layoutManager = layoutManager ?: LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = this
    if (this is LifecycleAdapter) {
        val context = recyclerView.context
        if (context is LifecycleOwner) {
            context.lifecycle.addObserver(this)
        }
    }
}