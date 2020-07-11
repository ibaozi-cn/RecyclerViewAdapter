package com.julive.adapter_demo

import com.julive.adapter.sorted.SortedModel

fun generateDataList() = (0..100).map {
    "$it"
}.toMutableList()
