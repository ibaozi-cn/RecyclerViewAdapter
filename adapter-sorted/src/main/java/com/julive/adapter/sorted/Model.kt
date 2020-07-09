package com.julive.adapter.sorted


abstract class SortedModel(
    /**
     * 排序用
     */
    val sortId: Int = 0,
    /**
     * 唯一值
     */
    val uniqueId: String = "") {

    fun <T : SortedModel> isSameModelAs(model: T?): Boolean {
        return uniqueId == model?.uniqueId
    }

    fun <T : SortedModel> isContentTheSameAs(model: T?): Boolean {
        return this == model
    }

    fun <T : SortedModel> compare(t: T?): Int {
        if (this.sortId > t?.sortId?:0) return 1
        if (this.sortId < t?.sortId?:0) return -1
        return 0
    }

}
