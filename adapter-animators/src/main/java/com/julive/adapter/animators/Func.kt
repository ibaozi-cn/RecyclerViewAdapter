package com.julive.adapter.animators

import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.RecyclerView

internal val add = fun(
    isFirstInit: Boolean,
    viewHolder: RecyclerView.ViewHolder,
    @AnimRes itemAnimationRes: Int
) {
    if (isFirstInit) {
        viewHolder.itemView.animation = AnimationUtils.loadAnimation(
            viewHolder.itemView.context,
            itemAnimationRes
        ).apply {
            start()
        }
    }
}
internal val update = fun(
    isFirstInit: Boolean,
    viewHolder: RecyclerView.ViewHolder,
    @AnimRes itemAnimationRes: Int
) {
    if (!isFirstInit) {
        viewHolder.itemView.animation = AnimationUtils.loadAnimation(
            viewHolder.itemView.context,
            itemAnimationRes
        ).apply {
            start()
        }
    }
}
