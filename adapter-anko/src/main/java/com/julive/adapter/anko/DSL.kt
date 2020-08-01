package com.julive.adapter.anko

import com.julive.adapter.core.DefaultViewHolder

fun <M>  ankoViewModelDsl(
    init: DefaultViewHolder<M>.() -> Unit
): AnkoViewModel<M> {
    return AnkoViewModel<M>().apply {
        onCreateViewHolder {
            init.invoke(this)
        }
    }
}