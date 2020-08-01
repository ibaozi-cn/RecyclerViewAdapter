package com.julive.adapter.core

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter_core.R
import java.lang.ClassCastException
import java.lang.Exception

abstract class ViewHolderCacheAdapter<VM : ViewModelType, VH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<VH>(), IAdapter<VM> {

    private val defaultViewHolderFactoryCache =
        DefaultViewHolderFactoryCache<ViewHolderFactory<VH>>()
    private val sparseArray = SparseArray<LayoutInflater>(1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val defaultViewHolder = defaultViewHolderFactoryCache[viewType].getViewHolder(
            parent,
            sparseArray.get(0) ?: LayoutInflater.from(parent.context)
        )
        defaultViewHolder.itemView.setTag(R.id.adapter, this)
        return defaultViewHolder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        if (position != RecyclerView.NO_POSITION) {
            holder.itemView.setTag(R.id.adapter, this)
            holder.itemView.setTag(R.id.adapter_item, getItem(position))
            holder as Subscriber<VH>
            holder.onBindViewHolder(holder, position, payloads)
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
        holder as Subscriber<VH>
        holder.unBindViewHolder(holder, holder.adapterPosition)
        holder.itemView.setTag(R.id.adapter_item, null)
        holder.itemView.setTag(R.id.adapter, null)
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