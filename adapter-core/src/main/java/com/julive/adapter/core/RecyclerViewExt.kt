package com.julive.adapter.core

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

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

fun RecyclerView.findFirstVisibleItemPosition(): Int {
    when (val manager = layoutManager) {
        is StaggeredGridLayoutManager -> {
            var position = manager.findFirstVisibleItemPositions(null)[0]
            for (i in 1 until getSpanCount()) {
                val nextPosition: Int = manager.findFirstVisibleItemPositions(null)[i]
                if (nextPosition < position) {
                    position = nextPosition
                }
            }
            return position
        }
        is LinearLayoutManager -> {
            return manager.findFirstVisibleItemPosition()
        }
        else -> {
            throw IllegalArgumentException("Not supported this :${manager?.javaClass?.name}")
        }
    }
}

fun RecyclerView.findLastVisibleItemPosition(): Int {
    when (val manager = layoutManager) {
        is StaggeredGridLayoutManager -> {
            var position = manager.findFirstVisibleItemPositions(null)[0]
            for (i in 1 until getSpanCount()) {
                val nextPosition: Int = manager.findLastVisibleItemPositions(null)[i]
                if (nextPosition > position) {
                    position = nextPosition
                }
            }
            return position
        }
        is LinearLayoutManager -> {
            return manager.findLastVisibleItemPosition()
        }
        else -> {
            throw IllegalArgumentException("Not supported this :${manager?.javaClass?.name}")
        }
    }
}

fun RecyclerView.getSpanCount(): Int {
    return when (val manager = layoutManager) {
        is GridLayoutManager -> {
            manager.spanCount
        }
        is StaggeredGridLayoutManager -> {
            manager.spanCount
        }
        else -> {
           return 1
        }
    }
}

fun RecyclerView.findFirstCompletelyVisibleItemPosition(): Int {
    when (val manager = layoutManager) {
        is StaggeredGridLayoutManager -> {
            var position = manager.findFirstCompletelyVisibleItemPositions(null)[0]
            for (i in 1 until getSpanCount()) {
                val nextPosition: Int = manager.findFirstCompletelyVisibleItemPositions(null)[i]
                if (nextPosition < position) {
                    position = nextPosition
                }
            }
            return position
        }
        is LinearLayoutManager -> {
            return manager.findFirstCompletelyVisibleItemPosition()
        }
        else -> {
            throw IllegalArgumentException("Not supported this :${manager?.javaClass?.name}")
        }
    }
}

fun RecyclerView.findLastCompletelyVisibleItemPosition(): Int {
    when (val manager = layoutManager) {
        is StaggeredGridLayoutManager -> {
            var position = manager.findLastCompletelyVisibleItemPositions(null)[0]
            for (i in 1 until getSpanCount()) {
                val nextPosition: Int = manager.findLastCompletelyVisibleItemPositions(null)[i]
                if (nextPosition > position) {
                    position = nextPosition
                }
            }
            return position
        }
        is LinearLayoutManager -> {
            return manager.findLastCompletelyVisibleItemPosition()
        }
        else -> {
            throw IllegalArgumentException("Not supported this :${manager?.javaClass?.name}")
        }
    }
}