package com.julive.adapter.core

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter_core.R
import java.lang.ref.WeakReference

abstract class ViewHolderCacheAdapter<VM : ViewModelType, VH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<VH>(), IAdapter<VM>, ILifecycleAdapter {

    private val defaultViewHolderFactoryCache = DefaultViewHolderFactoryCache<ViewHolderFactory<RecyclerView.ViewHolder>>()
    private val sparseArrayLayoutInflater = SparseArray<WeakReference<LayoutInflater>>(1)
    private var recyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val defaultViewHolder = defaultViewHolderFactoryCache[viewType].getViewHolder(
            parent, sparseArrayLayoutInflater.get(0).get() ?: LayoutInflater.from(parent.context)
        )
        defaultViewHolder.itemView.setTag(R.id.adapter, this)
        defaultViewHolder.itemView.setTag(R.id.adapter_recyclerView, recyclerView)
        return defaultViewHolder as VH
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        if (position != RecyclerView.NO_POSITION) {
            // Do your binding here
            holder.itemView.setTag(R.id.adapter, this)
            holder.itemView.setTag(R.id.adapter_recyclerView, recyclerView)
            val item = getItem(position) as? ViewModel<Any, VH>
            item?.let {
                holder.itemView.setTag(R.id.adapter_item, item)
                if (holder is Subscriber) {
                    holder.onBindViewHolder(position, payloads)
                }
                item.bindVH(holder, payloads)
                item.isFirstInit = false
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) ?: return 0
        val type = item.itemViewType
        if (!defaultViewHolderFactoryCache.contains(type)) {
            item as ViewHolderFactory<RecyclerView.ViewHolder>
            defaultViewHolderFactoryCache.register(type, item)
        }
        return type
    }

    override fun onViewRecycled(holder: VH) {
        (holder.itemView.getTag(R.id.adapter_item) as? ViewModel<*, VH>)?.apply {
            if (holder is Subscriber) {
                holder.unBindViewHolder(holder.adapterPosition)
            }
            unBindVH(holder)
        }
        holder.itemView.setTag(R.id.adapter_item, null)
        holder.itemView.setTag(R.id.adapter, null)
        holder.itemView.setTag(R.id.adapter_recyclerView, null)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val context = recyclerView.context
        sparseArrayLayoutInflater.append(0, WeakReference(LayoutInflater.from(context)))
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        defaultViewHolderFactoryCache.clear()
        sparseArrayLayoutInflater.clear()
        this.recyclerView = null
    }

    override fun onViewAttachedToWindow(holder: VH) {
        if (holder is Subscriber) {
            holder.onViewAttachedToWindow(holder, holder.adapterPosition)
        }
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        if (holder is Subscriber) {
            holder.onViewDetachedFromWindow(holder, holder.adapterPosition)
        }
    }

    override fun onDestroy(source: LifecycleOwner) {
        this.recyclerView?.adapter = null
    }

}