package com.julive.adapter.core


interface SortedModel {
    fun <T> isSameModelAs(model: T): Boolean
    fun <T> isContentTheSameAs(model: T): Boolean
    fun <T> compare(t: T): Int
}
