package com.julive.adapter.anko

import android.content.Context
import android.view.ViewGroup
import android.view.ViewManager
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter_anko.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.recyclerView(): RecyclerView = recyclerView() {}
inline fun ViewManager.recyclerView(init: RecyclerView.() -> Unit = {}): RecyclerView {
    return ankoView({ RecyclerView(it) }, theme = 0, init = init)
}

inline fun Context.recyclerView(): RecyclerView = recyclerView() {}
inline fun Context.recyclerView(init: RecyclerView.() -> Unit = {}): RecyclerView {
    return ankoView({ RecyclerView(it) }, theme = 0, init = init)
}

fun <AnkoView : AnkoComponent<ViewGroup>> RecyclerView.ViewHolder.getAnkoView(): AnkoView? {
    return itemView.getTag(R.id.list_adapter_anko_view) as? AnkoView
}