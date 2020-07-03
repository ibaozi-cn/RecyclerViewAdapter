package com.julive.adapter.core

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.bindListAdapter(
    listAdapter: ListAdapter<*, *>,
    layoutManager: RecyclerView.LayoutManager? = null
) {
    this.layoutManager = layoutManager ?: LinearLayoutManager(context)
    this.adapter = listAdapter
}


fun RecyclerView.bindMergeAdapter(){

}