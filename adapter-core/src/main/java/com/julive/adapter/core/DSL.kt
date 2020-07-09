package com.julive.adapter.core

fun arrayListAdapter(block: ArrayListAdapter.() -> Unit): ArrayListAdapter {
    return ArrayListAdapter().apply {
        block()
    }
}

fun <M> arrayItemViewModelDsl(
    layoutRes: Int,
    init: ArrayItemViewModel<M>.() -> Unit
): ArrayItemViewModel<M> {
    return ArrayItemViewModel<M>(layoutRes).apply {
        init()
    }
}