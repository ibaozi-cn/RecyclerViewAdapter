package com.julive.adapter.core

import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.observable.ObservableArrayList
import com.julive.adapter.observable.ObservableList.OnListChangedCallback

abstract class ObservableAdapter<VM : ViewModel<*, *, *>, VH : RecyclerView.ViewHolder> : ListAdapter<VM, VH>(), MutableCollection<VM> {

    private val observableDataList by lazy(LazyThreadSafetyMode.NONE) {
        ObservableArrayList<VM>()
    }

    init {

        observableDataList.addOnListChangedCallback(object :
            OnListChangedCallback<ObservableArrayList<VM>>() {

            override fun onChanged(sender: ObservableArrayList<VM>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(
                sender: ObservableArrayList<VM>,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeChanged(positionStart, itemCount)
            }

            override fun onItemRangeInserted(
                sender: ObservableArrayList<VM>,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeMoved(
                sender: ObservableArrayList<VM>,
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeRemoved(
                sender: ObservableArrayList<VM>,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }
        })

    }

    public override fun getItem(position: Int): VM {
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

    override fun add(e: VM): Boolean {
        return observableDataList.add(e)
    }

    override fun iterator(): MutableIterator<VM> {
        return observableDataList.iterator()
    }

    fun add(index: Int, element: VM) {
        observableDataList.add(index, element)
    }

    fun removeAt(index: Int): VM {
        return observableDataList.removeAt(index)
    }

    open fun set(index: Int, element: VM): VM {
        return observableDataList.set(index, element)
    }

    override val size: Int
        get() = observableDataList.size

    override fun contains(element: VM): Boolean {
        return observableDataList.contains(element)
    }

    override fun containsAll(elements: Collection<VM>): Boolean {
        return observableDataList.containsAll(elements)
    }

    override fun addAll(elements: Collection<VM>): Boolean {
        return observableDataList.addAll(elements)
    }

    override fun remove(element: VM): Boolean {
        return observableDataList.remove(element)
    }

    override fun removeAll(elements: Collection<VM>): Boolean {
        return observableDataList.removeAll(elements)
    }

    override fun retainAll(elements: Collection<VM>): Boolean {
        return observableDataList.retainAll(elements)
    }

    fun getAll(): List<VM> {
        return observableDataList
    }

}
