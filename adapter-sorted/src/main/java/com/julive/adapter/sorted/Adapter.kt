package com.julive.adapter.sorted

import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.julive.adapter.core.DefaultViewHolder
import com.julive.adapter.core.ListAdapter


/**
 * SortedList数据结构的适配器，自动排序，二分查找
 */
class SortedListAdapter : ListAdapter<SortedItemViewModel<*, *>, DefaultViewHolder>(),
    MutableCollection<SortedItemViewModel<*, *>> {
    private val sortedList by lazy {
        SortedList(
            SortedItemViewModel::class.java,
            object : SortedListAdapterCallback<SortedItemViewModel<*, *>>(this) {
                override fun areItemsTheSame(
                    item1: SortedItemViewModel<*, *>,
                    item2: SortedItemViewModel<*, *>
                ): Boolean {
                    return item1.model.isSameModelAs(item2.model)
                }

                override fun compare(
                    o1: SortedItemViewModel<*, *>,
                    o2: SortedItemViewModel<*, *>
                ): Int {
                    return o1.model.compare(o2.model)
                }

                override fun areContentsTheSame(
                    oldItem: SortedItemViewModel<*, *>,
                    newItem: SortedItemViewModel<*, *>
                ): Boolean {
                    return oldItem.model.isContentTheSameAs(newItem.model)
                }
            })
    }

    override val size: Int
        get() = sortedList.size()

    override fun contains(element: SortedItemViewModel<*, *>): Boolean {
        return sortedList.indexOf(element) > -1
    }

    override fun containsAll(elements: Collection<SortedItemViewModel<*, *>>): Boolean {
        throw  com.julive.adapter.sorted.SortedException()
    }

    override fun isEmpty(): Boolean {
        return sortedList.size() == 0
    }

    override fun add(element: SortedItemViewModel<*, *>): Boolean {
        return sortedList.add(element) > -1
    }

    override fun addAll(elements: Collection<SortedItemViewModel<*, *>>): Boolean {
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

    override fun iterator(): MutableIterator<SortedItemViewModel<*, *>> {
        throw com.julive.adapter.sorted.SortedException()
    }

    override fun remove(element: SortedItemViewModel<*, *>): Boolean {
        return sortedList.remove(element)
    }

    fun removeItemAt(index: Int) {
        if (sortedList.size() > 0)
            sortedList.removeItemAt(index)
    }

    override fun removeAll(elements: Collection<SortedItemViewModel<*, *>>): Boolean {
        sortedList.beginBatchedUpdates()
        elements.forEach {
            sortedList.remove(it)
        }
        sortedList.endBatchedUpdates()
        return true
    }

    override fun retainAll(elements: Collection<SortedItemViewModel<*, *>>): Boolean {
        throw com.julive.adapter.sorted.SortedException()
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }

    public override fun getItem(position: Int): SortedItemViewModel<*, *> {
        return sortedList.get(position)
    }

    public fun updateItem(position: Int, element: SortedItemViewModel<*, *>) {
        sortedList.updateItemAt(position, element)
    }

}
