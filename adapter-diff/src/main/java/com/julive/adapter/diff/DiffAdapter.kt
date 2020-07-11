package com.julive.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.julive.adapter.core.ArrayListAdapter

fun ArrayListAdapter.calculateDiff(
    newItems: List<ArrayViewModelDiffType>
) {
    val result = DiffUtil.calculateDiff(
        ArrayListAdapterCallBack(
            oldItems = getAll() as List<ArrayViewModelDiffType>,
            newItems = newItems
        )
    )
    replayAll(newItems)
    result.dispatchUpdatesTo(this)
}
