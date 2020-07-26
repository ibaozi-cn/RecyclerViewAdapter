package com.julive.adapter.core

interface IAdapter<VM> {
    fun getItem(position: Int): VM?
}

interface IListAdapter<VM>{
    fun add(vm: VM):Boolean = false
    fun set(index:Int,vm: VM){}
    fun updatePayload(index: Int,vm: VM){}
    fun remove(vm: VM):Boolean = false
    fun removeAt(index: Int){}
    fun clear(){}
}