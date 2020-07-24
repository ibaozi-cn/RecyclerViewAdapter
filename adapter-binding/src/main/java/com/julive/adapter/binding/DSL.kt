package com.julive.adapter.binding

fun <M> bindingViewModelDsl(
    layoutRes: Int,
    variableId: Int,
    init: BindingViewModel<M>.() -> Unit
): BindingViewModel<M> {
    return BindingViewModel<M>(layoutRes, variableId).apply {
        init()
    }
}