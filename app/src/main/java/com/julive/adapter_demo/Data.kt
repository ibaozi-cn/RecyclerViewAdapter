package com.julive.adapter_demo

import com.julive.adapter.sorted.SortedModel

fun generateDataList() = (0..100).map {
    "$it"
}.toMutableList()

/**
 * Model
 */
data class ModelTest(var title: String, var subTitle: String)

/**
 * sortedId 排序用
 * title 作为uniqueId ，只要一样就会出现RecyclerView ItemView被替换的情况
 */
data class SortedModelTest(val sortedId: Int, var title: String, var subTitle: String) : SortedModel(sortedId, title)