package com.julive.adapter.binding

import com.julive.adapter.core.DefaultViewHolder

fun <M> bindingViewModelDsl(
    layoutRes: Int,
    variableId: Int,
    init: DefaultViewHolder<M>.() -> Unit
): BindingViewModel<M> {
    return BindingViewModel<M>(layoutRes, variableId).apply {
        onCreateViewHolder {
            init(this)
        }
    }
}