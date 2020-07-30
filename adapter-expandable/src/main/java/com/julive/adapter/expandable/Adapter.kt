package com.julive.adapter.expandable

import android.util.SparseArray
import android.util.SparseBooleanArray
import com.julive.adapter.core.IAdapter

private val IAdapter<*>.expandedItems by lazy {
    SparseBooleanArray()
}

private val IAdapter<*>.expandConfig by lazy {
    SparseArray<Any>().apply {
        append(0, true) // is Multi Expandable
    }
}

var IAdapter<*>.isMultiExpand
    get() = expandConfig[0] as Boolean
    private set(value) {}

var IAdapter<*>.expandedCount: Int
    get() = expandedItems.size()
    private set(value) {}

fun IAdapter<*>.setMultiExpandable(enable: Boolean) {
    expandConfig.setValueAt(0, enable)
    if (!enable && expandedCount > 1) {
        clearExpanded()
    }
}

fun IAdapter<*>.getExpandedItems(): List<Int> {
    val size = expandedItems.size()
    val items: MutableList<Int> = ArrayList(size)
    for (i in 0 until size) {
        items.add(expandedItems.keyAt(i))
    }
    return items
}

fun IAdapter<*>.clearExpanded() {
    val selection = getExpandedItems()
    expandedItems.clear()
    for (i in selection) {
        notifyItemChanged(i)
    }
}

fun IAdapter<*>.isExpanded(position: Int) = getExpandedItems().contains(position)

fun IAdapter<*>.toggleExpand(position: Int) {
    if (!isMultiExpand) {
        clearExpanded()
    }
    if (expandedItems.get(position, false)) {
        expandedItems.delete(position)
    } else {
        expandedItems.put(position, true)
    }
    notifyItemChanged(position)
}
