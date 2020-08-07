package com.julive.adapter.core

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
            return 0
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
            1
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
            return 0
        }
    }
}

fun RecyclerView.findFirstCompletelyVisibleItemPosition():Int{
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
            return 0
        }
    }
}

fun RecyclerView.findLastCompletelyVisibleItemPosition():Int{
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
            return 0
        }
    }
}