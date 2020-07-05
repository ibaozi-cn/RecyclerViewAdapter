package com.julive.adapter.core

import android.view.View
import androidx.annotation.IdRes


abstract class ArrayItemViewModel<M> : ViewModel<M, DefaultViewHolder, ArrayListAdapter>() {

    var onItemClick: (() -> Unit?)? = null

    fun <T : View> getView(@IdRes id: Int): T? {
        return viewHolder.getView<T>(id) as? T
    }

    open fun reBindView() {
        adapter.set(position, this)
    }

}
