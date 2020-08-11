package com.julive.adapter.core

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface ViewHolderFactory<VH : RecyclerView.ViewHolder> {
    fun getViewHolder(parent: ViewGroup, layoutInflater: LayoutInflater): VH
}

interface ViewHolderFactoryCache<VHF : ViewHolderFactory<*>> {
    fun register(type: Int, item: VHF): Boolean
    operator fun get(type: Int): VHF
    fun contains(type: Int): Boolean
    fun clear()
}

class DefaultViewHolderFactoryCache : ViewHolderFactoryCache<ViewModelType> {
    private val typeInstances = SparseArray<ViewModelType>()
    override fun register(type: Int, item: ViewModelType): Boolean {
        if (!contains(type)) {
            typeInstances.put(type, item)
            return true
        }
        return false
    }
    override fun get(type: Int): ViewModelType {
        return typeInstances.get(type)
    }
    override fun contains(type: Int) = typeInstances.indexOfKey(type) >= 0
    override fun clear() {
        typeInstances.clear()
    }
}