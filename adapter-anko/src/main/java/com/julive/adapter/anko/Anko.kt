package com.julive.adapter.anko

import android.content.Context
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

fun <T> AnkoComponent<T>.create(context: Context, t: T) = createView(AnkoContext.Companion.create(context, t))
