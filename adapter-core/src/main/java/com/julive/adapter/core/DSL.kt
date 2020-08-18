package com.julive.adapter.core

inline fun listAdapter(block: ListAdapter.() -> Unit): ListAdapter = ListAdapter().apply {
    block()
}

inline fun <M> layoutViewModelDsl(
    layoutRes: Int,
    model: M,
    crossinline init: DefaultViewHolder.() -> Unit
) = LayoutViewModel<M>(layoutRes).apply {
    this.model = model
    onCreateViewHolder {
        init.invoke(this)
    }
}
