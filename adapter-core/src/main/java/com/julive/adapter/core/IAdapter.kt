package com.julive.adapter.core

interface IAdapter<VM> {
    fun getItem(position: Int): VM?
}
