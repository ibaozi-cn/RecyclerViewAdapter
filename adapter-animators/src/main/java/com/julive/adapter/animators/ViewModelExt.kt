package com.julive.adapter.animators

import androidx.annotation.AnimRes
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.ViewModelType

fun ViewModelType.firstAnimation(
    viewHolder: RecyclerView.ViewHolder,
    @AnimRes itemAnimationRes: Int = R.anim.item_animation_from_right
) = add(isFirstInit, viewHolder, itemAnimationRes)

fun ViewModelType.updateAnimation(
    viewHolder: RecyclerView.ViewHolder,
    @AnimRes itemAnimationRes: Int = R.anim.item_animation_scale
) = update(isFirstInit, viewHolder, itemAnimationRes)
