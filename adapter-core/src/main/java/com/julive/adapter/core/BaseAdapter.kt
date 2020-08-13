package com.julive.adapter.core

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter_core.R
import java.lang.ref.WeakReference

@Suppress("UNCHECKED_CAST")
abstract class BaseAdapter<VM : ViewModelType> : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IAdapter<ViewModelType>, LifecycleAdapter {

    private val defaultViewHolderFactoryCache = DefaultViewHolderFactoryCache()
    private val sparseArrayLayoutInflater = SparseArray<WeakReference<LayoutInflater>>(1)
    private var recyclerView: RecyclerView? = null
    override val arrayLifeObservers: SparseArray<(source: LifecycleOwner, event: Lifecycle.Event) -> Boolean> =
        SparseArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val defaultViewHolder = defaultViewHolderFactoryCache[viewType].getViewHolder(
            parent, sparseArrayLayoutInflater.get(0).get() ?: LayoutInflater.from(parent.context)
        )
        defaultViewHolder.itemView.setTag(R.id.adapter, this)
        defaultViewHolder.itemView.setTag(R.id.adapter_recyclerView, recyclerView)
        return defaultViewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (position != RecyclerView.NO_POSITION) {
            // Do your binding here
            holder.itemView.setTag(R.id.adapter, this)
            holder.itemView.setTag(R.id.adapter_recyclerView, recyclerView)
            getItem(position)?.apply {
                this as ViewModel<*, RecyclerView.ViewHolder>
                holder.itemView.setTag(R.id.adapter_item, this)
                if (holder is Subscriber) {
                    holder.onBindViewHolder(position, payloads)
                }
                bindVH(holder, payloads)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) ?: return 0
        defaultViewHolderFactoryCache.register(item.itemViewType, item)
        return item.itemViewType
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (holder is Subscriber) {
            holder.unBindViewHolder(holder.adapterPosition)
        }
        holder.getViewModel<ViewModel<*, RecyclerView.ViewHolder>>()?.apply {
            unBindVH(holder)
        }
        holder.itemView.setTag(R.id.adapter_item, null)
        holder.itemView.setTag(R.id.adapter, null)
        holder.itemView.setTag(R.id.adapter_recyclerView, null)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        sparseArrayLayoutInflater.append(0, WeakReference(LayoutInflater.from(recyclerView.context)))
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        defaultViewHolderFactoryCache.clear()
        sparseArrayLayoutInflater.clear()
        this.recyclerView = null
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        if (holder is Subscriber) {
            holder.onViewAttachedToWindow(holder, holder.adapterPosition)
            holder.getViewModel<ViewModelType>()?.isFirstInit = false
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        if (holder is Subscriber) {
            holder.onViewDetachedFromWindow(holder, holder.adapterPosition)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            this.recyclerView?.adapter = null
            source.lifecycle.removeObserver(this)
        }
        super.onStateChanged(source, event)
    }
}