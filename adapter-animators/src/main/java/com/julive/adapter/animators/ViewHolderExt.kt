package com.julive.adapter.animators

import androidx.annotation.AnimRes
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.ViewModelType
import com.julive.adapter.core.getViewModel

fun RecyclerView.ViewHolder.firstAnimation(
    @AnimRes itemAnimationRes: Int = R.anim.item_animation_from_right
) = add(getViewModel<ViewModelType>()?.isFirstInit ?: false, this, itemAnimationRes)

fun RecyclerView.ViewHolder.updateAnimation(
    @AnimRes itemAnimationRes: Int = R.anim.item_animation_scale
) = update(getViewModel<ViewModelType>()?.isFirstInit ?: false, this, itemAnimationRes)