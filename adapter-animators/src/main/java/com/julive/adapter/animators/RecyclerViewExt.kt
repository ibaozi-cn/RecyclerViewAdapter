package com.julive.adapter.animators

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

fun RecyclerView.calculateAnimationDelay(
    position: Int,
    delayOffset: Int
): Int {

    var delay = delayOffset

    var firstVisiblePosition = findFirstCompletelyVisibleItemPosition()
    var lastVisiblePosition = findLastCompletelyVisibleItemPosition()

    if (firstVisiblePosition < 0 && position >= 0) {
        firstVisiblePosition = position - 1
    }

    if (position - 1 > lastVisiblePosition) {
        lastVisiblePosition = position - 1
    }

    val visibleItems = lastVisiblePosition - firstVisiblePosition
    val numberOfAnimatedItems = position - 1

    if (visibleItems < numberOfAnimatedItems) {
        val numColumns = getSpanCount()
        val n = position % numColumns
        delay += delayOffset * n
    } else {
        delay = position * delayOffset / 2
    }

    return delay
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