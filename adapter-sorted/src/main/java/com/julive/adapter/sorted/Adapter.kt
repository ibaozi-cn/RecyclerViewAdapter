package com.julive.adapter.sorted

import androidx.recyclerview.widget.SortedList
import com.julive.adapter.core.*

class SortedListAdapter : BaseAdapter<ViewModelType>(){

    private val sortedList by lazy {
        SortedList(
            ViewModel::class.java,
            SortedCallBack(this)
        )
    }

    fun contains(element: ViewModelType): Boolean {
        return sortedList.indexOf(element) > -1
    }

    fun add(vm: ViewModelType): Boolean {
        return sortedList.add(vm) > -1
    }

    fun addAll(elements: Collection<ViewModelType>): Boolean {
        sortedList.beginBatchedUpdates()
        elements.forEach {
            sortedList.add(it)
        }
        sortedList.endBatchedUpdates()
        return true
    }

    fun clear() {
        sortedList.clear()
    }

    fun remove(vm: ViewModelType): Boolean {
        return sortedList.remove(vm)
    }

    fun removeAll(elements: Collection<ViewModelType>): Boolean {
        sortedList.beginBatchedUpdates()
        elements.forEach {
            sortedList.remove(it)
        }
        sortedList.endBatchedUpdates()
        return true
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }

    override fun getItem(position: Int): ViewModelType {
        return sortedList.get(position)
    }

    fun set(index: Int, vm: ViewModelType) {
        sortedList.updateItemAt(index, vm)
    }

    fun removeAt(index: Int) {
        if (index < sortedList.size() && index > -1)
            sortedList.removeItemAt(index)
    }

}
