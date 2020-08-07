package com.julive.adapter.animators

import android.content.Context
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.*
import java.util.*

internal val animationArray by lazy {
    WeakHashMap<Int, Animation>()
}

private fun loadAnimation(context: Context, @AnimRes itemAnimationRes: Int, key: Int): Animation {
    return animationArray[key] ?: AnimationUtils.loadAnimation(context, itemAnimationRes).apply {
        animationArray[key] = this
    }
}

fun RecyclerView.ViewHolder.animationWithDelayOffset(
    isEnableAnimation: Boolean,
    @AnimRes itemAnimationRes: Int,
    delayOffset: Int,
    recyclerView: RecyclerView?
) {
    if (isEnableAnimation) {
        itemView.clearAnimation()
        val key = itemView.hashCode()
        val delay = recyclerView?.calculateAnimationDelay(adapterPosition, delayOffset)
        itemView.animation =
            loadAnimation(itemView.context, itemAnimationRes, key).apply {
                startOffset = delay?.toLong() ?: 0L
                if (hasEnded()) {
                    start()
                }
            }
        recyclerView?.setTag(R.id.last_delay_animation_position, adapterPosition)
    }
}

fun RecyclerView.ViewHolder.animation(
    isEnableAnimation: Boolean,
    @AnimRes itemAnimationRes: Int
) {
    if (isEnableAnimation) {
        itemView.clearAnimation()
        val key = itemView.hashCode() + 1
        itemView.animation = loadAnimation(itemView.context, itemAnimationRes, key).apply {
            if (this.hasEnded()) {
                this.start()
            }
        }
    }
}

fun DefaultViewHolder.firstAnimation(
    @AnimRes itemAnimationRes: Int = R.anim.item_animation_from_right,
    delayOffset: Int = 100
) {
    var isEnableAnimation = true
    val lastDelayAnimationPosition = getRecyclerView()?.getTag(R.id.last_delay_animation_position)
    lastDelayAnimationPosition?.let {
        it as Int
        if (adapterPosition <= it) {
            isEnableAnimation = false
        }
    }
    animationWithDelayOffset(
        isEnableAnimation,
        itemAnimationRes,
        delayOffset,
        getRecyclerView()
    )
}


fun DefaultViewHolder.updateAnimation(
    @AnimRes itemAnimationRes: Int = R.anim.item_animation_scale
) = animation(!(getViewModel<ViewModelType>()?.isFirstInit ?: false), itemAnimationRes)