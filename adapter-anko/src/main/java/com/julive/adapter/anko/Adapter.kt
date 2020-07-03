package com.julive.adapter.anko

import android.util.SparseArray
import android.view.ViewGroup
import com.julive.adapter.core.ArrayItemViewModel
import com.julive.adapter.core.ObservableAdapter
import org.jetbrains.anko.AnkoComponent

class AnkoListAdapter : ObservableAdapter<AnkoItemViewModel<*, *>, AnkoViewHolder<*>>() {

    private val layoutsAnko: SparseArray<AnkoComponent<ViewGroup>> by lazy(LazyThreadSafetyMode.NONE) { SparseArray<AnkoComponent<ViewGroup>>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnkoViewHolder<*> {
        return AnkoViewHolder(layoutsAnko[viewType], parent)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        layoutsAnko.append(item.itemViewType, item.ankoView)
        return item.itemViewType
    }

    override fun onBindViewHolder(holder: AnkoViewHolder<*>, position: Int) {
        getItem(position).ankoView = holder.ankoView
        super.onBindViewHolder(holder, position)
    }

    override fun set(index: Int, element: AnkoItemViewModel<*, *>): AnkoItemViewModel<*, *> {
        if (contains(element))
            element.onBindView(this)
        else
            return super.set(index, element)
        return element
    }

}
