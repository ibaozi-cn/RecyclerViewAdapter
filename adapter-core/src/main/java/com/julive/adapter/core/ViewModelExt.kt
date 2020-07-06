package com.julive.adapter.core

import android.view.View
import androidx.annotation.IdRes

abstract class ArrayItemViewModel<M, VH : DefaultViewHolder> :
    ViewModel<M, VH, ArrayListAdapter>() {

    fun <T : View> getView(@IdRes id: Int): T? {
        return viewHolder.getView<T>(id) as? T
    }

}

