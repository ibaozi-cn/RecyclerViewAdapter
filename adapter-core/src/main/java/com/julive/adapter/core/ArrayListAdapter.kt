package com.julive.adapter.core

import com.julive.adapter.observable.ObservableArrayList

/**
 * ArrayList数据结构，所有特性跟随ArrayList
 */
class ArrayListAdapter :
    ListAdapter<ArrayViewModelType<*>, DefaultViewHolder>(),
    MutableCollection<ArrayViewModelType<*>> {

    private val observableDataList by lazy(LazyThreadSafetyMode.NONE) {
        ObservableArrayList<ArrayViewModelType<*>>()
    }

    init {
        observableDataList.addOnListChangedCallback(OnItemListChangeCallback(this))
    }

    override fun getItem(position: Int): ArrayViewModelType<*> {
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

    override fun add(e: ArrayViewModelType<*>): Boolean {
        return observableDataList.add(e)
    }

    override fun iterator(): MutableIterator<ArrayViewModelType<*>> {
        return observableDataList.iterator()
    }

    fun add(index: Int, element: ArrayViewModelType<*>) {
        observableDataList.add(index, element)
    }

    fun removeAt(index: Int): ArrayViewModelType<*> {
        return observableDataList.removeAt(index)
    }

    fun set(index: Int, element: ArrayViewModelType<*>): ArrayViewModelType<*> {
        observableDataList[index] = element
        return element
    }

    override val size: Int
        get() = observableDataList.size

    override fun contains(element: ArrayViewModelType<*>): Boolean {
        return observableDataList.contains(element)
    }

    override fun containsAll(elements: Collection<ArrayViewModelType<*>>): Boolean {
        return observableDataList.containsAll(elements)
    }

    override fun addAll(elements: Collection<ArrayViewModelType<*>>): Boolean {
        return observableDataList.addAll(elements)
    }

    override fun remove(element: ArrayViewModelType<*>): Boolean {
        return observableDataList.remove(element)
    }

    override fun removeAll(elements: Collection<ArrayViewModelType<*>>): Boolean {
        return observableDataList.removeAll(elements)
    }

    override fun retainAll(elements: Collection<ArrayViewModelType<*>>): Boolean {
        return observableDataList.retainAll(elements)
    }

    fun getAll(): List<ArrayViewModelType<*>> {
        return observableDataList
    }

    fun indexOf(element: ArrayViewModelType<*>): Int {
        return observableDataList.indexOf(element)
    }

}
