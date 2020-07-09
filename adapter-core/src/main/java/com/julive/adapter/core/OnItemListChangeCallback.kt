package com.julive.adapter.core

import com.julive.adapter.observable.ObservableArrayList
import com.julive.adapter.observable.ObservableList

class OnItemListChangeCallback<VM>(private val listAdapter: ArrayListAdapter) :
    ObservableList.OnListChangedCallback<ObservableArrayList<VM>>() {

    override fun onChanged(sender: ObservableArrayList<VM>) {
        listAdapter.notifyDataSetChanged()
    }

    override fun onItemRangeChanged(
        sender: ObservableArrayList<VM>,
        positionStart: Int,
        itemCount: Int
    ) {
        listAdapter.notifyItemRangeChanged(positionStart, itemCount)
    }

    override fun onItemRangeInserted(
        sender: ObservableArrayList<VM>,
        positionStart: Int,
        itemCount: Int
    ) {
        listAdapter.notifyItemRangeInserted(positionStart, itemCount)
    }

    override fun onItemRangeMoved(
        sender: ObservableArrayList<VM>,
        fromPosition: Int,
        toPosition: Int,
        itemCount: Int
    ) {
        listAdapter.notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemRangeRemoved(
        sender: ObservableArrayList<VM>,
        positionStart: Int,
        itemCount: Int
    ) {
        listAdapter.notifyItemRangeRemoved(positionStart, itemCount)
    }
}