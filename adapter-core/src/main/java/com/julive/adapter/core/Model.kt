package com.julive.adapter.core

interface SameModel {
    var uniqueId: String
    fun <T : SameModel> isSameModelAs(model: T): Boolean {
        return this.uniqueId == model.uniqueId
    }
    fun <T : SameModel> isContentTheSameAs(model: T): Boolean {
        return this == model
    }
    /**
     * RecyclerView ItemView中更小局部的刷新
     */
    fun <T : SameModel> getChangePayload(newItem: T): Any? = null
}


