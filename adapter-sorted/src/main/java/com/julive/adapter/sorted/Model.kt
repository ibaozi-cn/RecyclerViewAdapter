package com.julive.adapter.sorted

import com.julive.adapter.core.SameModel

interface SortedModel : SameModel {
    /**
     * 排序使用
     */
    fun <T : SortedModel> compare(model: T): Int
}
