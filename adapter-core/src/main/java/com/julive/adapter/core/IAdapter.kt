package com.julive.adapter.core

interface IAdapter<VM> {
    fun getItem(position: Int): VM?
    fun notifyItemChanged(position: Int)
}

interface IListAdapter<VM>{
    fun add(vm: VM):Boolean = false
    fun set(index:Int,vm: VM?){}
    fun remove(vm: VM):Boolean = false
    fun removeAt(index: Int){}
    fun clear(){}
}