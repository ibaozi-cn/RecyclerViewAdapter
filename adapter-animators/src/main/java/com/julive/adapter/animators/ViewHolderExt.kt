package com.julive.adapter.animators

import android.content.Context
import android.util.SparseArray
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.DefaultViewHolder
import com.julive.adapter.core.ViewModelType
import com.julive.adapter.core.getViewModel

private var delayPosition = 0
private val animationArray = SparseArray<Animation>()

private fun loadAnimation(context: Context, @AnimRes itemAnimationRes: Int, key: Int): Animation {
    return animationArray[key] ?: AnimationUtils.loadAnimation(context, itemAnimationRes).apply {
        animationArray.append(key, this)
    }
}

fun RecyclerView.onDestroy(){
    animationArray.clear()
}

fun RecyclerView.ViewHolder.animationWithDelayOffset(
    isEnableAnimation: Boolean,
    @AnimRes itemAnimationRes: Int,
    delayOffset: Int
) {
    if (isEnableAnimation) {
        itemView.clearAnimation()
        val currentPosition = ++delayPosition
        val delay = currentPosition * delayOffset / 2
        itemView.animation =
            loadAnimation(itemView.context, itemAnimationRes, currentPosition).apply {
                startOffset = delay.toLong()
                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {
                    }
                    override fun onAnimationEnd(p0: Animation?) {
                        delayPosition = 0
                    }
                    override fun onAnimationStart(p0: Animation?) {
                    }
                })
                if (this.hasEnded()) {
                    this.start()
                }
            }
    }
}

fun RecyclerView.ViewHolder.animation(
    isEnableAnimation: Boolean,
    @AnimRes itemAnimationRes: Int
) {
    if (isEnableAnimation) {
        itemView.clearAnimation()
        itemView.animation = AnimationUtils.loadAnimation(
            itemView.context,
            itemAnimationRes
        )
    }
}

fun DefaultViewHolder.firstAnimation(
    @AnimRes itemAnimationRes: Int = R.anim.item_animation_from_right,
    delayOffset: Int = 200
) = animationWithDelayOffset(
    getViewModel<ViewModelType>()?.isFirstInit ?: false,
    itemAnimationRes,
    delayOffset
)

fun DefaultViewHolder.updateAnimation(
    @AnimRes itemAnimationRes: Int = R.anim.item_animation_scale
) = animation(!(getViewModel<ViewModelType>()?.isFirstInit ?: false), itemAnimationRes)