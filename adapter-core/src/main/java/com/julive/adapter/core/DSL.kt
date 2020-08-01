package com.julive.adapter.core

fun listAdapter(block: ListAdapter.() -> Unit): ListAdapter {
    return ListAdapter().apply {
        block()
    }
}

fun <M> layoutViewModelDsl(
    layoutRes: Int,
    init: DefaultViewHolder<M>.() -> Unit
): LayoutViewModel<M> {
    return LayoutViewModel<M>(layoutRes).apply {
        onCreateViewHolder {
            init(this)
        }
    }
}