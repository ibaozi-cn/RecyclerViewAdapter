package com.julive.adapter.core

import android.view.View
import androidx.annotation.IdRes


abstract class ArrayItemViewModel<M> : ViewModel<M, DefaultViewHolder, ArrayListAdapter>() {

    fun <T : View> getView(@IdRes id: Int): T? {
        return viewHolder.getView<T>(id) as? T
    }

    open fun reBindView() {
        adapter.set(position, this)
    }

}
//todo 自动排序版本，待实现
abstract class SortedListViewModel<M : SortedModel>() : ViewModel<M, DefaultViewHolder, ArrayListAdapter>()