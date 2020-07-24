package com.julive.adapter.core

interface IAdapter<VM> {
    fun getItem(position: Int): VM?
    fun add(vm: VM):Boolean
    fun set(index:Int,vm: VM)
    fun remove(vm: VM):Boolean
    fun removeAt(index: Int)
    fun clear()
}
