package com.julive.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.julive.adapter.core.ListAdapter
import java.lang.Exception

fun ListAdapter.calculateDiff(
    newItems: List<ViewModelDiffType>
) {
    val result = DiffUtil.calculateDiff(
        ArrayListAdapterCallBack(
            oldItems = dataList as? List<ViewModelDiffType>
                ?: throw Exception("please let model implements SameModel"),
            newItems = newItems
        )
    )
    replayAll(newItems)
    result.dispatchUpdatesTo(this)
}
