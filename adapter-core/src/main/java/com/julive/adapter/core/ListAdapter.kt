package com.julive.adapter.core

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter_core.R
import java.util.*

abstract class ListAdapter<VM : ViewModel<*,*,*>, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>(), IAdapter<VM> {

    private val defaultViewHolderFactoryCache = DefaultViewHolderFactoryCache<ViewHolderFactory<VH>>()
    private val sparseArray = SparseArray<LayoutInflater>(1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val defaultViewHolder = defaultViewHolderFactoryCache[viewType].getViewHolder(parent, sparseArray.get(0) ?: LayoutInflater.from(parent.context))
        defaultViewHolder.itemView.setTag(R.id.list_adapter, this)
        return defaultViewHolder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, position, Collections.emptyList())
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        if(position != RecyclerView.NO_POSITION){
            // Do your binding here
            holder.itemView.setTag(R.id.list_adapter, this)
            val item = getItem(position) as? ViewModel<Any, RecyclerView.ViewHolder, IAdapter<*>>
            item?.let {
                item.adapter = this
                item.model?.let { it1 -> item.bindVH(holder, it1, payloads) }
                holder.itemView.setTag(R.id.list_adapter_item, item)
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) ?: return 0
        val type = item.itemViewType
        if (!defaultViewHolderFactoryCache.contains(type)) {
            item as ViewHolderFactory<VH>
            defaultViewHolderFactoryCache.register(type, item)
        }
        return type
    }

    override fun onViewRecycled(holder: VH) {
        (holder.itemView.getTag(R.id.list_adapter_item) as ViewModel<*, VH, *>).apply {
            unBindVH(holder)
        }
        holder.itemView.setTag(R.id.list_adapter_item, null)
        holder.itemView.setTag(R.id.list_adapter, null)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val context = recyclerView.context
        sparseArray.append(0, LayoutInflater.from(context))
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        defaultViewHolderFactoryCache.clear()
        sparseArray.clear()
    }
}