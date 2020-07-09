package com.julive.adapter.paging

interface PageModel{
    /**
     * 唯一值
     */
    val uniqueId: String
    fun <T : PageModel> isSameModelAs(model: T?): Boolean {
        val bol = this.uniqueId == model?.uniqueId
        return bol
    }
    fun <T : PageModel> isContentTheSameAs(model: T?): Boolean {
        val bol =  this == model
        return bol
    }
}
