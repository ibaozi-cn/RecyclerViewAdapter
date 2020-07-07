package com.julive.adapter.core

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

abstract class ArrayItemViewModel<M> : ViewModel<M, DefaultViewHolder<M>, ArrayListAdapter>() {

    fun <T : View> getView(@IdRes id: Int): T? {
        return viewHolder.getView<T>(id) as? T
    }

    override fun unBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {

    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder?,
        model: M,
        payloads: MutableList<Any>?
    ) {

    }
}

