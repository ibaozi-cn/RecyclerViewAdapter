package com.julive.adapter.anko

import android.content.Context
import android.view.ViewManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.custom.ankoView


inline fun ViewManager.recyclerView(): RecyclerView = recyclerView() {}
inline fun ViewManager.recyclerView(init: RecyclerView.() -> Unit = {}): RecyclerView {
    return ankoView({ RecyclerView(it) }, theme = 0, init = init)
}

inline fun Context.recyclerView(): RecyclerView = recyclerView() {}
inline fun Context.recyclerView(init: RecyclerView.() -> Unit = {}): RecyclerView {
    return ankoView({ RecyclerView(it) }, theme = 0, init = init)
}