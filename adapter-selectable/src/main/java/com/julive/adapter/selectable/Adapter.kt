package com.julive.adapter.selectable

import android.util.SparseBooleanArray
import com.julive.adapter.core.IAdapter

private val IAdapter<*>.selectedItems by lazy {
    SparseBooleanArray()
}

fun IAdapter<*>.getSelectedItems(): List<Int> {
    val items: MutableList<Int> = ArrayList(selectedItems.size())
    for (i in 0 until selectedItems.size()) {
        items.add(selectedItems.keyAt(i))
    }
    return items
}

fun IAdapter<*>.isSelected(position: Int) = getSelectedItems().contains(position)

fun IAdapter<*>.selectedCount() = selectedItems.size()

fun IAdapter<*>.clearSelection() {
    val selection = getSelectedItems()
    selectedItems.clear()
    for (i in selection) {
        notifyItemChanged(i)
    }
}

fun IAdapter<*>.toggleSelection(position: Int) {
    if (selectedItems.get(position, false)) {
        selectedItems.delete(position)
    } else {
        selectedItems.put(position, true)
    }
    notifyItemChanged(position);
}