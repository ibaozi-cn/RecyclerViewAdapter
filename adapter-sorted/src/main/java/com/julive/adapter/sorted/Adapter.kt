package com.julive.adapter.sorted

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.julive.adapter.core.*

/**
 * SortedList数据结构的适配器，自动排序，二分查找
 */
class SortedListAdapter :
    ViewHolderCacheAdapter<ViewModelType, RecyclerView.ViewHolder>(),
    MutableCollection<ViewModelType> {

    private val sortedList by lazy {
        SortedList(
            ViewModel::class.java,
            SortedCallBack(this)
        )
    }

    override val size: Int
        get() = sortedList.size()

    override fun contains(element: ViewModelType): Boolean {
        return sortedList.indexOf(element) > -1
    }

    override fun containsAll(elements: Collection<ViewModelType>): Boolean {
        throw  com.julive.adapter.sorted.SortedException()
    }

    override fun isEmpty(): Boolean {
        return sortedList.size() == 0
    }

    override fun add(vm: ViewModelType): Boolean {
        return sortedList.add(vm) > -1
    }

    override fun addAll(elements: Collection<ViewModelType>): Boolean {
        sortedList.beginBatchedUpdates()
        elements.forEach {
            sortedList.add(it)
        }
        sortedList.endBatchedUpdates()
        return true
    }

    override fun clear() {
        sortedList.clear()
    }

    override fun iterator(): MutableIterator<ViewModelType> {
        throw com.julive.adapter.sorted.SortedException()
    }

    override fun remove(vm: ViewModelType): Boolean {
        return sortedList.remove(vm)
    }

    override fun removeAll(elements: Collection<ViewModelType>): Boolean {
        sortedList.beginBatchedUpdates()
        elements.forEach {
            sortedList.remove(it)
        }
        sortedList.endBatchedUpdates()
        return true
    }

    override fun retainAll(elements: Collection<ViewModelType>): Boolean {
        throw com.julive.adapter.sorted.SortedException()
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }

    override fun getItem(position: Int): ViewModelType {
        return sortedList.get(position) as ViewModelType
    }

    fun set(index: Int, vm: ViewModelType) {
        sortedList.updateItemAt(index, vm)
    }

    fun removeAt(index: Int) {
        if (index < sortedList.size() && index > -1)
            sortedList.removeItemAt(index)
    }

}
