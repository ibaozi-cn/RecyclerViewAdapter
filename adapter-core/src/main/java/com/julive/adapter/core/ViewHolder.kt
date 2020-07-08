package com.julive.adapter.core

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter_core.R

typealias ViewModelType = ViewModel<out Any, out ViewHolderType, out ListAdapter<*, *>>

typealias ViewHolderType = DefaultViewHolder<out Any>

abstract class DefaultViewHolder<Model>(val view: View) : RecyclerView.ViewHolder(view) {
    /**
     * views缓存
     */
    private val views: SparseArray<View> = SparseArray()

    fun <T : View> getView(@IdRes viewId: Int): T? {
        return retrieveView(viewId)
    }

    private fun <T : View> retrieveView(@IdRes viewId: Int): T? {
        var view = views[viewId]
        if (view == null) {
            view = itemView.findViewById(viewId)
            if (view == null) return null
            views.put(viewId, view)
        }
        return view as T
    }

    fun unBindViewHolder(viewHolder: RecyclerView.ViewHolder) {

    }

    fun <Adapter : ListAdapter<*, *>> getAdapter(): Adapter? {
        return this.itemView.getTag(R.id.list_adapter) as? Adapter
    }

}

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