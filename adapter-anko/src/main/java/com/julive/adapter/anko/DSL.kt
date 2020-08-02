package com.julive.adapter.anko

import android.view.ViewGroup
import com.julive.adapter.core.DefaultViewHolder
import org.jetbrains.anko.AnkoComponent

fun <M, AnkoView : AnkoComponent<ViewGroup>> ankoViewModelDsl(
    model: M,
    ankoView: () -> AnkoView,
    init: DefaultViewHolder.() -> Unit
): AnkoViewModel<M, AnkoView> {
    return AnkoViewModel<M, AnkoView>().apply {
        onCreateView {
            ankoView()
        }
        this.model = model
        onCreateViewHolder {
            init.invoke(this)
        }
    }
}
