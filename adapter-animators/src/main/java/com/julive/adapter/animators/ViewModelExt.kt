package com.julive.adapter.animators

import androidx.annotation.AnimRes
import com.julive.adapter.core.DefaultViewHolder
import com.julive.adapter.core.ViewModelType
import com.julive.adapter.core.getRecyclerView

fun ViewModelType.firstAnimation(
    viewHolder: DefaultViewHolder,
    @AnimRes itemAnimationRes: Int = R.anim.item_animation_from_right,
    delayOffset: Int = 200
) = viewHolder.animationWithDelayOffset(
    isFirstInit,
    itemAnimationRes,
    delayOffset,
    viewHolder.getRecyclerView()
)

fun ViewModelType.updateAnimation(
    viewHolder: DefaultViewHolder,
    @AnimRes itemAnimationRes: Int = R.anim.item_animation_scale
) = viewHolder.animation(!isFirstInit, itemAnimationRes)
