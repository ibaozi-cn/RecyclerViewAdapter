package com.julive.adapter.core


/**
 * 抽象为List数据结构
 */
class ListAdapter :
    ViewHolderCacheAdapter<ViewModelType, DefaultViewHolder>(), IListAdapter<ViewModelType>,
    MutableCollection<ViewModelType> {
    /**
     * 默认ArrayList数据结构
     */
    private var dataList = mutableListOf<ViewModelType>()

    override fun getItem(position: Int): ViewModelType {
        return dataList[position]
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun clear() {
        val oldSize = size
        dataList.clear()
        if (oldSize != 0) {
            notifyItemRangeRemoved(0, oldSize)
        }
    }

    override fun isEmpty(): Boolean {
        return dataList.isEmpty()
    }

    override fun add(vm: ViewModelType): Boolean {
        val result = dataList.add(vm)
        notifyItemRangeInserted(size - 1, 1)
        return result
    }

    override fun iterator(): MutableIterator<ViewModelType> {
        return dataList.iterator()
    }

    fun add(index: Int, element: ViewModelType) {
        dataList.add(index, element)
        notifyItemRangeInserted(index, 1)
    }

    override fun removeAt(index: Int) {
        val vm = dataList.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun set(index: Int, vm: ViewModelType) {
        dataList[index] = vm
        notifyItemChanged(index)
    }

    override fun updatePayload(index: Int, vm: ViewModelType) {
        dataList[index] = vm
        notifyItemChanged(index, vm.model)
    }

    override val size: Int
        get() = dataList.size

    override fun contains(element: ViewModelType): Boolean {
        return dataList.contains(element)
    }

    override fun containsAll(elements: Collection<ViewModelType>): Boolean {
        return dataList.containsAll(elements)
    }

    override fun addAll(elements: Collection<ViewModelType>): Boolean {
        val oldSize = size
        val added = dataList.addAll(elements)
        if (added) {
            notifyItemRangeInserted(oldSize, size - oldSize)
        }
        return added
    }

    override fun remove(vm: ViewModelType): Boolean {
        val index = indexOf(vm)
        if (index >= 0) {
            removeAt(index)
            return true
        }
        return false
    }

    /**
     * 不会触发Adapter更新
     */
    override fun removeAll(elements: Collection<ViewModelType>): Boolean {
        return dataList.removeAll(elements)
    }

    override fun retainAll(elements: Collection<ViewModelType>): Boolean {
        return dataList.retainAll(elements)
    }

    fun getAll(): List<ViewModelType> {
        return dataList
    }

    fun replayAll(list: List<ViewModelType>) {
        dataList.clear()
        dataList.addAll(list)
    }

}
