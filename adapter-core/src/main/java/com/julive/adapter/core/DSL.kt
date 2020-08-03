package com.julive.adapter.core


fun listAdapter(block: ListAdapter.() -> Unit): ListAdapter {
    return ListAdapter().apply {
        block()
    }
}

fun <M> layoutViewModelDsl(
    layoutRes: Int,
    model:M,
    init: DefaultViewHolder.() -> Unit
): LayoutViewModel<M> {
    return LayoutViewModel<M>(layoutRes).apply {
        this.model = model
        onCreateViewHolder {
            init.invoke(this)
        }
    }
}