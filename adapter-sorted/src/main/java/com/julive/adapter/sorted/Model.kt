package com.julive.adapter.sorted

import com.julive.adapter.core.SameModel

interface SortedModel : SameModel {
    fun <T> compare(t: T): Int
}
