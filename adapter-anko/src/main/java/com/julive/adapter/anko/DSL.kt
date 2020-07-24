package com.julive.adapter.anko

import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent

fun <M, AnkoView : AnkoComponent<ViewGroup>> ankoViewModelDsl(
    init: AnkoViewModel<M, AnkoView>.() -> Unit
): AnkoViewModel<M, AnkoView> {
    return AnkoViewModel<M, AnkoView>().apply {
        init()
    }
}