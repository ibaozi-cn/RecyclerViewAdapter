package com.julive.adapter.core

import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup

class ArrayListAdapter : ObservableAdapter<ArrayItemViewModel<*>, DefaultViewHolder>() {

    private val layouts: SparseIntArray by lazy(LazyThreadSafetyMode.NONE) { SparseIntArray() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        return DefaultViewHolder(
            LayoutInflater.from(parent.context).inflate(layouts[viewType], parent, false)
        ).also { vh ->
            vh.itemView.setOnClickListener {
                val position = vh.adapterPosition
                val item = getItem(position)
                item.onItemClick?.invoke()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        layouts.append(item.itemViewType, item.layoutRes)
        return item.itemViewType
    }

    override fun set(index: Int, element: ArrayItemViewModel<*>): ArrayItemViewModel<*> {
        if (contains(element))
            element.onBindView(this)
        else
            return super.set(index, element)
        return element
    }

}
