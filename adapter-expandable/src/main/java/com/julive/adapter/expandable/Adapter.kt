package com.julive.adapter.expandable

import android.util.SparseArray
import android.util.SparseBooleanArray
import com.julive.adapter.core.IAdapter
import java.lang.ref.WeakReference

private val expandedItemsCache = SparseArray<WeakReference<SparseBooleanArray?>?>()
private val expandConfigCache = SparseArray<WeakReference<SparseArray<Any>?>?>()

private val defaultExpandConfig by lazy {
    SparseArray<Any>().apply {
        append(0, true) // is Multi Expandable
    }
}

private fun getExpandedItems(key: Int): SparseBooleanArray {
    val wr = expandedItemsCache[key]
    val sba by lazy {
        SparseBooleanArray()
    }
    return if (wr == null) {
        expandedItemsCache.append(key, WeakReference(sba))
        sba
    } else {
        val expandedItems = wr.get()
        if (expandedItems == null) {
            expandedItemsCache.append(key, WeakReference(sba))
        }
        expandedItems ?: sba
    }
}

private fun getExpandConfig(key: Int): SparseArray<Any> {
    val wr = expandConfigCache[key]
    return if (wr == null) {
        expandConfigCache.append(key, WeakReference(defaultExpandConfig))
        defaultExpandConfig
    } else {
        val expandConfig = wr.get()
        if (expandConfig == null) {
            expandConfigCache.append(key, WeakReference(defaultExpandConfig))
        }
        expandConfig ?: defaultExpandConfig
    }
}

var IAdapter<*>.isMultiExpand: Boolean
    get() = getExpandConfig(hashCode())[0] as Boolean
    private set(value) {}

var IAdapter<*>.expandedCount: Int
    get() = getExpandedItems(hashCode()).size()
    private set(value) {}

fun IAdapter<*>.setMultiExpandable(enable: Boolean) {
    getExpandConfig(hashCode()).setValueAt(0, enable)
    if (!enable && expandedCount > 1) {
        clearExpanded()
    }
}

fun IAdapter<*>.getExpandedItems(): List<Int> {
    val eis = getExpandedItems(hashCode())
    val size = eis.size()
    val items: MutableList<Int> = ArrayList(size)
    for (i in 0 until size) {
        items.add(eis.keyAt(i))
    }
    return items
}

fun IAdapter<*>.clearExpanded() {
    val selection = getExpandedItems()
    getExpandedItems(hashCode()).clear()
    for (i in selection) {
        notifyItemChanged(i)
    }
}

fun IAdapter<*>.isExpanded(position: Int) = getExpandedItems().contains(position)

fun IAdapter<*>.toggleExpand(position: Int) {
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