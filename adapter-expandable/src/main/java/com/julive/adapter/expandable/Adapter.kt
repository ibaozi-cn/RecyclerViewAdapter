package com.julive.adapter.expandable

import android.util.SparseArray
import android.util.SparseBooleanArray
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

private val expandedItemsCache = SparseArray<SparseBooleanArray>()
private val expandConfigCache = SparseArray<SparseArray<Any>>()

private val defaultExpandConfig by lazy {
    SparseArray<Any>().apply {
        append(0, true) // is Multi Expandable
    }
}

private fun getExpandedItems(key: Int): SparseBooleanArray {
    return expandedItemsCache[key] ?: SparseBooleanArray().apply {
        expandedItemsCache.append(key, this)
    }
}

private fun getExpandConfig(key: Int): SparseArray<Any> {
    return expandConfigCache[key] ?: defaultExpandConfig.apply {
        expandConfigCache.append(key, this)
    }
}

var RecyclerView.Adapter<*>.isMultiExpand: Boolean
    get() = getExpandConfig(hashCode())[0] as Boolean
    private set(value) {}

var RecyclerView.Adapter<*>.expandedCount: Int
    get() = getExpandedItems(hashCode()).size()
    private set(value) {}

fun RecyclerView.Adapter<*>.setMultiExpandable(enable: Boolean) {
    getExpandConfig(hashCode()).setValueAt(0, enable)
    if (!enable && expandedCount >= 1) {
        clearExpanded()
    }
}

fun RecyclerView.Adapter<*>.getExpandedItems(): List<Int> {
    val eis = getExpandedItems(hashCode())
    val size = eis.size()
    val items: MutableList<Int> = ArrayList(size)
    for (i in 0 until size) {
        items.add(eis.keyAt(i))
    }
    return items
}

fun RecyclerView.Adapter<*>.clearExpanded() {
    val selection = getExpandedItems()
    getExpandedItems(hashCode()).clear()
    for (i in selection) {
        notifyItemChanged(i)
    }
}

fun RecyclerView.Adapter<*>.isExpanded(position: Int) = getExpandedItems().contains(position)

fun RecyclerView.Adapter<*>.toggleExpand(position: Int) {
    if (!isMultiExpand) {
        clearExpanded()
    }
    val eis = getExpandedItems(hashCode())
    if (eis.get(position, false)) {
        eis.delete(position)
    } else {
        eis.put(position, true)
    }
    notifyItemChanged(position)
}

fun RecyclerView.Adapter<*>.onDestroy() {
    val key = hashCode()
    expandedItemsCache[key]?.clear()
    expandConfigCache[key]?.clear()
}