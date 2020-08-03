package com.julive.adapter.core

interface IAdapter<VM> {
    fun getItem(position: Int): VM?
    fun notifyItemChanged(position: Int)
}

interface IListAdapter<VM>{
    fun add(vm: VM):Boolean = false
    fun add(index: Int, element: ViewModelType)
    fun set(index:Int,vm: VM?){}
    fun updatePayload(index: Int,vm: VM){}
    fun addAll(elements: Collection<ViewModelType>): Boolean
    fun remove(vm: VM):Boolean = false
    fun removeAt(index: Int){}
    fun clear(){}
}