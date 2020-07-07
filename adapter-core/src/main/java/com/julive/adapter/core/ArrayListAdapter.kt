package com.julive.adapter.core

import com.julive.adapter.observable.ObservableArrayList
import com.julive.adapter.observable.ObservableList.OnListChangedCallback

/**
 * ArrayList数据结构，所有特性跟随ArrayList
 */
open class ArrayListAdapter : ListAdapter<ViewModelType, ViewHolderType>(), MutableCollection<ViewModelType> {

    private val observableDataList by lazy(LazyThreadSafetyMode.NONE) {
        ObservableArrayList<ViewModelType>()
    }

    init {
        observableDataList.addOnListChangedCallback(object :
            OnListChangedCallback<ObservableArrayList<ViewModelType>>() {

            override fun onChanged(sender: ObservableArrayList<ViewModelType>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(
                sender: ObservableArrayList<ViewModelType>,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeChanged(positionStart, itemCount)
            }

            override fun onItemRangeInserted(
                sender: ObservableArrayList<ViewModelType>,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeMoved(
                sender: ObservableArrayList<ViewModelType>,
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeRemoved(
                sender: ObservableArrayList<ViewModelType>,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }
        })
    }

    public override fun getItem(position: Int): ViewModelType {
        return observableDataList[position]
    }

    override fun getItemCount(): Int {
        return observableDataList.size
    }

    override fun clear() {
        return observableDataList.clear()
    }

    override fun isEmpty(): Boolean {
        return observableDataList.isEmpty()
    }

    override fun add(e: ViewModelType): Boolean {
        return observableDataList.add(e)
    }

    override fun iterator(): MutableIterator<ViewModelType> {
        return observableDataList.iterator()
    }

    fun add(index: Int, element: ViewModelType) {
        observableDataList.add(index, element)
    }

    fun removeAt(index: Int): ViewModelType {
        return observableDataList.removeAt(index)
    }

    open fun set(index: Int, element: ViewModelType): ViewModelType {
        observableDataList.set(index, element)
        return element
    }

    override val size: Int
        get() = observableDataList.size

    override fun contains(element: ViewModelType): Boolean {
        return observableDataList.contains(element)
    }

    override fun containsAll(elements: Collection<ViewModelType>): Boolean {
        return observableDataList.containsAll(elements)
    }

    override fun addAll(elements: Collection<ViewModelType>): Boolean {
        return observableDataList.addAll(elements)
    }

    override fun remove(element: ViewModelType): Boolean {
        return observableDataList.remove(element)
    }

    override fun removeAll(elements: Collection<ViewModelType>): Boolean {
        return observableDataList.removeAll(elements)
    }

    override fun retainAll(elements: Collection<ViewModelType>): Boolean {
        return observableDataList.retainAll(elements)
    }

    fun getAll(): List<ViewModelType> {
        return observableDataList
    }

    fun indexOf(element: ViewModelType): Int {
        return observableDataList.indexOf(element)
    }

}
