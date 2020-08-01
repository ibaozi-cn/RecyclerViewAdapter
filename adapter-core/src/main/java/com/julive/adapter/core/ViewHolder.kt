package com.julive.adapter.core

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

typealias BindView<M> = DefaultViewHolder<M>.(M, payloads: List<Any>) -> Unit
typealias UnBindView<M> = DefaultViewHolder<M>.() -> Unit
typealias InitView<M> = DefaultViewHolder<M>.() -> Unit

@Suppress("UNCHECKED_CAST")
open class DefaultViewHolder<M>(open val view: View) : RecyclerView.ViewHolder(view),
    Subscriber<DefaultViewHolder<M>> {

    private val views by lazy {
        SparseArray<View>()
    }

    private fun <T : View> retrieveView(@IdRes viewId: Int): T? {
        var view = views[viewId]
        if (view == null) {
            view = itemView.findViewById(viewId)
            if (view == null) return null
            views.put(viewId, view)
        }
        return view as? T
    }

    fun <T : View> getView(@IdRes viewId: Int): T? {
        return retrieveView(viewId)
    }

    private var bindView: BindView<M>? = null
    private var unBindView: UnBindView<M>? = null

    fun onBindViewHolder(f: DefaultViewHolder<M>.(M, payloads: List<Any>) -> Unit) {
        bindView = f
    }

    fun onUnBindViewHolder(f: DefaultViewHolder<M>.() -> Unit) {
        unBindView = f
    }

    override fun onBindViewHolder(
        viewHolder: DefaultViewHolder<M>,
        position: Int,
        payloads: List<Any>
    ) {
        val m = viewHolder.getModel<M>()
        m?.let {
            bindView?.invoke(viewHolder, it, payloads)
        }
    }

    override fun unBindViewHolder(viewHolder: DefaultViewHolder<M>, position: Int) {
        unBindView?.invoke(viewHolder)
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