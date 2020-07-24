package com.julive.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.julive.adapter.core.ListAdapter

fun ListAdapter.calculateDiff(
    newItems: List<ViewModelDiffType>
) {
    val result = DiffUtil.calculateDiff(
        ArrayListAdapterCallBack(
            oldItems = getAll() as List<ViewModelDiffType>,
            newItems = newItems
        )
    )
    replayAll(newItems)
    result.dispatchUpdatesTo(this)
}
