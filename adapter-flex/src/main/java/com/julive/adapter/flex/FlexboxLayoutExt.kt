package com.julive.adapter.flex

import android.content.Context
import android.util.AttributeSet
import com.google.android.flexbox.*

fun Context.flexboxLayoutMangerDefault(block: FlexboxLayoutManager.() -> Unit = {}): FlexboxLayoutManager {
    val layoutManager = FlexboxLayoutManager(this).apply {
        justifyContent = JustifyContent.FLEX_START
        flexDirection = FlexDirection.COLUMN
    }
    layoutManager.block()
    return layoutManager
}

fun Context.flexboxLayoutManager(block: FlexboxLayoutManager.() -> Unit = {}): FlexboxLayoutManager {
    val layoutManager = FlexboxLayoutManager(this)
    layoutManager.block()
    return layoutManager
}

fun Context.flexboxLayout(block: FlexboxLayout.() -> Unit = {}): FlexboxLayout {
    val layoutManager = FlexboxLayout(this)
    layoutManager.block()
    return layoutManager
}