package com.julive.adapter.binding

import com.julive.adapter.core.DefaultViewHolder

fun <M> bindingViewModelDsl(
    layoutRes: Int,
    variableId: Int,
    model:M,
    init: DefaultViewHolder.() -> Unit
): BindingViewModel<M> {
    return BindingViewModel<M>(layoutRes, variableId).apply {
        this.model = model
        onCreateViewHolder(init)
    }
}