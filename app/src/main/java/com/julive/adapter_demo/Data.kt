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
data class SortedModelTest(val sortedId: Int, var title: String, var subTitle: String) : SortedModel {
    override fun <T> compare(t: T): Int {
        if (sortedId > (t as? SortedModelTest)?.sortedId ?: 0) return 1
        if (sortedId < (t as? SortedModelTest)?.sortedId ?: 0) return -1
        return 0
    }

    override fun <T> isSameModelAs(model: T): Boolean {
        return this == model
    }

    override fun <T> isContentTheSameAs(model: T): Boolean {
        return this.title == (model as? SortedModelTest)?.title
    }
}