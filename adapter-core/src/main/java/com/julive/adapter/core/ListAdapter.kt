package com.julive.adapter.core

import androidx.recyclerview.widget.RecyclerView

class ListAdapter : ViewHolderCacheAdapter<ViewModelType, RecyclerView.ViewHolder>(), IListAdapter<ViewModelType> {

    var dataList = mutableListOf<ViewModelType>()

    override fun getItem(position: Int): ViewModelType {
        return dataList[position]
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun clear() {
        val oldSize = itemCount
        dataList.clear()
        if (oldSize != 0) {
            notifyItemRangeRemoved(0, oldSize)
        }
    }

    override fun add(vm: ViewModelType): Boolean {
        val result = dataList.add(vm)
        notifyItemRangeInserted(itemCount - 1, 1)
        return result
    }

    override fun add(index: Int, element: ViewModelType) {
        dataList.add(index, element)
        notifyItemRangeInserted(index, 1)
    }

    override fun removeAt(index: Int) {
        val vm = dataList.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun set(index: Int, vm: ViewModelType?) {
        if (vm != null) {
            dataList[index] = vm
            notifyItemChanged(index)
        }
    }

    override fun updatePayload(index: Int, vm: ViewModelType) {
        dataList[index] = vm
        notifyItemChanged(index, vm.model)
    }

    override fun addAll(elements: Collection<ViewModelType>): Boolean {
        val oldSize = itemCount
        val added = dataList.addAll(elements)
        if (added) {
            notifyItemRangeInserted(oldSize, itemCount - oldSize)
        }
        return added
    }

    override fun remove(vm: ViewModelType): Boolean {
        val index = dataList.indexOf(vm)
        if (index >= 0) {
            removeAt(index)
            return true
        }
        return false
    }

    fun replayAll(list: List<ViewModelType>) {
        dataList.clear()
        dataList.addAll(list)
    }

}
