package com.julive.adapter.selectable

import android.util.SparseArray
import android.util.SparseBooleanArray
import com.julive.adapter.core.IAdapter
import java.lang.ref.WeakReference


private val selectedItemsCache = SparseArray<WeakReference<SparseBooleanArray?>?>()
private val selectConfigCache = SparseArray<WeakReference<SparseArray<Any>?>?>()

private val defaultSelectedConfig by lazy {
    SparseArray<Any>().apply {
        append(0, true) // is Multi Selectable
        append(1, Int.MAX_VALUE) // Selectable Max Size Default Int.Max
    }
}

private fun getSelectedItems(key: Int): SparseBooleanArray {
    val wr = selectedItemsCache[key]
    val sba by lazy {
        SparseBooleanArray()
    }
    return if (wr == null) {
        selectedItemsCache.append(key, WeakReference(sba))
        sba
    } else {
        val expandedItems = wr.get()
        if (expandedItems == null) {
            selectedItemsCache.append(key, WeakReference(sba))
        }
        expandedItems ?: sba
    }
}

private fun getSelectConfig(key: Int): SparseArray<Any> {
    val wr = selectConfigCache[key]
    return if (wr == null) {
        selectConfigCache.append(key, WeakReference(defaultSelectedConfig))
        defaultSelectedConfig
    } else {
        val expandConfig = wr.get()
        if (expandConfig == null) {
            selectConfigCache.append(key, WeakReference(defaultSelectedConfig))
        }
        expandConfig ?: defaultSelectedConfig
    }
}

var IAdapter<*>.isMultiSelect
    get() = getSelectConfig(hashCode())[0] as Boolean
    private set(value) {}

var IAdapter<*>.selectedMaxSize: Int
    get() = getSelectConfig(hashCode())[1] as Int
    private set(value) {}

var IAdapter<*>.selectedCount: Int
    get() = getSelectedItems(hashCode()).size()
    private set(value) {}

fun IAdapter<*>.setMultiSelectable(enable: Boolean) {
    getSelectConfig(hashCode()).setValueAt(0, enable)
    if (!enable && selectedCount > 1) {
        clearSelection()
    }
}

fun IAdapter<*>.setSelectableMaxSize(size: Int) {
    getSelectConfig(hashCode()).setValueAt(1, size)
}

fun IAdapter<*>.getSelectedItems(): List<Int> {
    val si = getSelectedItems(hashCode())
    val itemSize = si.size()
    val items: MutableList<Int> = ArrayList(itemSize)
    for (i in 0 until itemSize) {
        items.add(si.keyAt(i))
    }
    return items
}

fun IAdapter<*>.isSelected(position: Int) = getSelectedItems().contains(position)

fun IAdapter<*>.clearSelection() {
    val selection = getSelectedItems()
    getSelectedItems(hashCode()).clear()
    for (i in selection) {
        notifyItemChanged(i)
    }
}

fun IAdapter<*>.toggleSelection(position: Int, isMaxSelect: ((Boolean) -> Unit)? = null) {
    val si = getSelectedItems(hashCode())
    val isSelect = si.get(position, false)
    if (selectedCount >= selectedMaxSize && !isSelect) {
        isMaxSelect?.invoke(true)
        return
    }
    isMaxSelect?.invoke(false)
    if (!isMultiSelect) {
        clearSelection()
    }
    if (isSelect) {
        si.delete(position)
    } else {
        si.put(position, true)
    }
    notifyItemChanged(position)
}