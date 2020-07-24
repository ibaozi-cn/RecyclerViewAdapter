package com.julive.adapter.core

fun listAdapter(block: ListAdapter.() -> Unit): ListAdapter {
    return ListAdapter().apply {
        block()
    }
}

fun <M> layoutViewModelDsl(
    layoutRes: Int,
    init: LayoutViewModel<M>.() -> Unit
): LayoutViewModel<M> {
    return LayoutViewModel<M>(layoutRes).apply {
        init()
    }
}