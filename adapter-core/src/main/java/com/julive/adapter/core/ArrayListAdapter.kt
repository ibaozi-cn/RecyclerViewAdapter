package com.julive.adapter.core

import com.julive.adapter.observable.ObservableArrayList

/**
 * ArrayList数据结构，所有特性跟随ArrayList
 */
class ArrayListAdapter :
    ListAdapter<ViewModelType, DefaultViewHolder>(),
    MutableCollection<ViewModelType> {

    private val observableDataList by lazy(LazyThreadSafetyMode.NONE) {
        ObservableArrayList<ViewModelType>()
    }

    init {
        observableDataList.addOnListChangedCallback(OnItemListChangeCallback(this))
    }

    override fun getItem(position: Int): ViewModelType {
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

    override fun add(element: ViewModelType): Boolean {
        return observableDataList.add(element)
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

    fun set(index: Int, element: ViewModelType): ViewModelType {
        observableDataList[index] = element
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

    /**
     * 不会触发Adapter更新
     */
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

     fun replayAll(list: List<*>) {
        observableDataList.removeAll()
        observableDataList.addAllOnly(list as List<ViewModelType>)
    }

}
