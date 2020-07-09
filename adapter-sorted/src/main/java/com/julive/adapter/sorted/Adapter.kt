package com.julive.adapter.sorted

import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.julive.adapter.core.DefaultViewHolder
import com.julive.adapter.core.ListAdapter

/**
 * SortedList数据结构的适配器，自动排序，二分查找
 */
class SortedListAdapter : ListAdapter<SortedItemVMType, DefaultViewHolder>(),
    MutableCollection<SortedItemVMType> {
    private val sortedList by lazy {
        SortedList(
            SortedItemViewModel::class.java,
            object : SortedListAdapterCallback<SortedItemVMType>(this) {
                override fun areItemsTheSame(
                    item1: SortedItemVMType,
                    item2: SortedItemVMType
                ): Boolean {
                    return item1.model?.isSameModelAs(item2.model)?:false
                }

                override fun compare(
                    o1: SortedItemVMType,
                    o2: SortedItemVMType
                ): Int {
                    return o1.model?.compare(o2.model)?:0
                }

                override fun areContentsTheSame(
                    oldItem: SortedItemVMType,
                    newItem: SortedItemVMType
                ): Boolean {
                    return oldItem.model?.isContentTheSameAs(newItem.model)?:false
                }
            })
    }

    override val size: Int
        get() = sortedList.size()

    override fun contains(element: SortedItemVMType): Boolean {
        return sortedList.indexOf(element) > -1
    }

    override fun containsAll(elements: Collection<SortedItemVMType>): Boolean {
        throw  com.julive.adapter.sorted.SortedException()
    }

    override fun isEmpty(): Boolean {
        return sortedList.size() == 0
    }

    override fun add(element: SortedItemVMType): Boolean {
        return sortedList.add(element) > -1
    }

    override fun addAll(elements: Collection<SortedItemVMType>): Boolean {
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

    override fun iterator(): MutableIterator<SortedItemVMType> {
        throw com.julive.adapter.sorted.SortedException()
    }

    override fun remove(element: SortedItemVMType): Boolean {
        return sortedList.remove(element)
    }

    fun removeItemAt(index: Int) {
        if (sortedList.size() > 0)
            sortedList.removeItemAt(index)
    }

    override fun removeAll(elements: Collection<SortedItemVMType>): Boolean {
        sortedList.beginBatchedUpdates()
        elements.forEach {
            sortedList.remove(it)
        }
        sortedList.endBatchedUpdates()
        return true
    }

    override fun retainAll(elements: Collection<SortedItemVMType>): Boolean {
        throw com.julive.adapter.sorted.SortedException()
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }

    public override fun getItem(position: Int): SortedItemVMType {
        return sortedList.get(position)
    }

    public fun updateItem(position: Int, element: SortedItemVMType) {
        sortedList.updateItemAt(position, element)
    }

}
