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

    fun <T : SortedModel> isSameModelAs(model: T): Boolean {
        val bol = this.uniqueId == model.uniqueId
        return bol
    }

    fun <T : SortedModel> isContentTheSameAs(model: T): Boolean {
        val bol =  this == model
        return bol
    }

    fun <T : SortedModel> compare(t: T): Int {
        if (this.sortId > t.sortId) return 1
        if (this.sortId < t.sortId) return -1
        return 0
    }

}
