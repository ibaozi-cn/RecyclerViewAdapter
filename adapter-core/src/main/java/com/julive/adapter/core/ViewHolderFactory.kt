package com.julive.adapter.core

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter_core.R

typealias GenericViewHolderFactory = ViewHolderFactory<out RecyclerView.ViewHolder>

interface ViewHolderFactory<VH : RecyclerView.ViewHolder> {
    fun getViewHolder(parent: ViewGroup, layoutInflater: LayoutInflater): VH
}

interface ViewHolderFactoryCache<VHF : GenericViewHolderFactory> {
    fun register(type: Int, item: VHF): Boolean
    operator fun get(type: Int): VHF
    fun contains(type: Int): Boolean
    fun clear()
}

class DefaultViewHolderFactoryCache<VHF : GenericViewHolderFactory> : ViewHolderFactoryCache<VHF> {
    private val typeInstances = SparseArray<VHF>()
    override fun register(type: Int, item: VHF): Boolean {
        if (typeInstances.indexOfKey(type) < 0) {
            typeInstances.put(type, item)
            return true
        }
        return false
    }

    override fun get(type: Int): VHF {
        return typeInstances.get(type)
    }

    override fun contains(type: Int) = typeInstances.indexOfKey(type) >= 0
    override fun clear() {
        typeInstances.clear()
    }
}