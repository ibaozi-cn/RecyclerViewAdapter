package com.julive.adapter_demo

fun generateDataList() = (0..100).map {
    "$it"
}.toMutableList()