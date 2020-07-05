package com.julive.adapter.observable

interface ObservableList<T> : List<T> {

    fun addOnListChangedCallback(callback: OnListChangedCallback<out ObservableList<T>>)


    fun removeOnListChangedCallback(callback: OnListChangedCallback<out ObservableList<T>>)


    abstract class OnListChangedCallback<T : ObservableList<*>> {

        abstract fun onChanged(sender: T)


        abstract fun onItemRangeChanged(sender: T, positionStart: Int, itemCount: Int)


        abstract fun onItemRangeInserted(sender: T, positionStart: Int, itemCount: Int)


        abstract fun onItemRangeMoved(
            sender: T, fromPosition: Int, toPosition: Int,
            itemCount: Int
        )

        abstract fun onItemRangeRemoved(sender: T, positionStart: Int, itemCount: Int)
    }
}

open class ObservableArrayList<T> : ArrayList<T>(), ObservableList<T> {

    @Transient
    private var mListeners: ListChangeRegistry = ListChangeRegistry()

    override fun addOnListChangedCallback(callback: ObservableList.OnListChangedCallback<out ObservableList<T>>) {
        mListeners.add(callback)
    }

    override fun removeOnListChangedCallback(callback: ObservableList.OnListChangedCallback<out ObservableList<T>>) {
        mListeners.remove(callback)
    }

    override fun add(element: T): Boolean {
        super.add(element)
        notifyAdd(size - 1, 1)
        return true
    }

    override fun add(index: Int, element: T) {
        super.add(index, element)
        notifyAdd(index, 1)
    }

    override fun addAll(collection: Collection<T>): Boolean {
        val oldSize = size
        val added = super.addAll(collection)
        if (added) {
            notifyAdd(oldSize, size - oldSize)
        }
        return added
    }

    override fun addAll(index: Int, collection: Collection<T>): Boolean {
        val added = super.addAll(index, collection)
        if (added) {
            notifyAdd(index, collection.size)
        }
        return added
    }

    override fun clear() {
        val oldSize = size
        super.clear()
        if (oldSize != 0) {
            notifyRemove(0, oldSize)
        }
    }

    override fun remove(element: T): Boolean {
        val index = indexOf(element)
        if (index >= 0) {
            removeAt(index)
            return true
        }
        return false
    }

    override fun removeAt(index: Int): T {
        notifyRemove(index, 1);
        return super.removeAt(index)
    }

    override fun set(index: Int, element: T): T {
        val va = super.set(index, element)
        mListeners.notifyChanged(this, index, 1)
        return va
    }

    override fun removeRange(fromIndex: Int, toIndex: Int) {
        super.removeRange(fromIndex, toIndex)
        notifyRemove(fromIndex, toIndex - fromIndex)
    }

    private fun notifyAdd(start: Int, count: Int) {
        mListeners.notifyInserted(this, start, count)
    }

    private fun notifyRemove(start: Int, count: Int) {
        mListeners.notifyRemoved(this, start, count)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        elements.forEach {
            remove(it)
        }
        return super.removeAll(elements)
    }

}
