package com.julive.adapter.core

fun arrayListAdapter(block: ArrayListAdapter.() -> Unit): ArrayListAdapter {
    return ArrayListAdapter().apply {
        block()
    }
}

fun <M> arrayItemViewModelDsl(
    init: ArrayItemViewModelDsl<M>.() -> Unit
): ArrayItemViewModelDsl<M> {
    return ArrayItemViewModelDsl<M>().apply {
        init()
    }
}