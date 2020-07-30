package com.julive.adapter.selectable

import android.util.SparseArray
import android.util.SparseBooleanArray
import com.julive.adapter.core.IAdapter

private val IAdapter<*>.selectedItems by lazy {
    SparseBooleanArray()
}

private val IAdapter<*>.selectConfig by lazy {
    SparseArray<Any>().apply {
        append(0, true) // is Multi Selectable
        append(1, Int.MAX_VALUE) // Selectable Max Size Default Int.Max
    }
}

var IAdapter<*>.isMultiSelect
    get() = selectConfig[0] as Boolean
    private set(value) {}

var IAdapter<*>.selectedMaxSize: Int
    get() = selectConfig[1] as Int
    private set(value) {}

var IAdapter<*>.selectedCount: Int
    get() = selectedItems.size()
    private set(value) {}

fun IAdapter<*>.setMultiSelectable(enable: Boolean) {
    selectConfig.setValueAt(0, enable)
    if (!enable && selectedCount > 1) {
        clearSelection()
    }
}

fun IAdapter<*>.setSelectableMaxSize(size: Int) {
    selectConfig.setValueAt(1, size)
}

fun IAdapter<*>.getSelectedItems(): List<Int> {
    val itemSize = selectedItems.size()
    val items: MutableList<Int> = ArrayList(itemSize)
    for (i in 0 until itemSize) {
        items.add(selectedItems.keyAt(i))
    }
    return items
}

fun IAdapter<*>.isSelected(position: Int) = getSelectedItems().contains(position)

fun IAdapter<*>.clearSelection() {
    val selection = getSelectedItems()
    selectedItems.clear()
    for (i in selection) {
        notifyItemChanged(i)
    }
}

fun IAdapter<*>.toggleSelection(position: Int, isMaxSelect: ((Boolean) -> Unit)? = null) {
    val isSelect = selectedItems.get(position, false)
    if (selectedCount >= selectedMaxSize && !isSelect) {
        isMaxSelect?.invoke(true)
        return
    }
    isMaxSelect?.invoke(false)
    if (!isMultiSelect) {
        clearSelection()
    }
    if (isSelect) {
        selectedItems.delete(position)
    } else {
        selectedItems.put(position, true)
    }
    notifyItemChanged(position)
}