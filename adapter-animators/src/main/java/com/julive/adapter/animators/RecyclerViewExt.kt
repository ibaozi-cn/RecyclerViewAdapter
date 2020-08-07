package com.julive.adapter.animators

import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.*

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