package com.julive.adapter.core

interface SameModel {
    fun <T> isSameModelAs(model: T): Boolean
    fun <T> isContentTheSameAs(model: T): Boolean
}