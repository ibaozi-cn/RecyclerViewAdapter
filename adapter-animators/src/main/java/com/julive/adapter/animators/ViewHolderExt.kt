package com.julive.adapter.animators

import android.content.Context
import android.util.Log
import android.util.SparseArray
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.*

private val animationArray by lazy {
    SparseArray<Animation>()
}

private fun loadAnimation(context: Context, @AnimRes itemAnimationRes: Int, key: Int): Animation {
    return animationArray[key] ?: AnimationUtils.loadAnimation(context, itemAnimationRes).apply {
        animationArray.append(key, this)
    }
}

fun RecyclerView.onDestroy() {
    animationArray.clear()
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
        val delay = calculateAnimationDelay(adapterPosition, delayOffset, recyclerView)
        itemView.animation =
            loadAnimation(itemView.context, itemAnimationRes, key).apply {
                startOffset = delay.toLong()
                setAnimationListener(object :Animation.AnimationListener{
                    override fun onAnimationRepeat(p0: Animation?) {
                    }
                    override fun onAnimationEnd(p0: Animation?) {
                        itemView.clearAnimation()
                    }
                    override fun onAnimationStart(p0: Animation?) {
                        Log.d("onAnimationStart","adapterPosition:$adapterPosition")
                    }
                })
                if (this.hasEnded()) {
                    this.start()
                }
            }
    }
}

fun calculateAnimationDelay(
    position: Int,
    delayOffset: Int,
    recyclerView: RecyclerView?
): Int {

    var delay = delayOffset

    var firstVisiblePosition = recyclerView?.findFirstCompletelyVisibleItemPosition() ?: RecyclerView.NO_POSITION
    var lastVisiblePosition = recyclerView?.findLastCompletelyVisibleItemPosition() ?: RecyclerView.NO_POSITION

    if (firstVisiblePosition < 0 && position >= 0) {
        firstVisiblePosition = position - 1
    }

    if (position - 1 > lastVisiblePosition) {
        lastVisiblePosition = position - 1
    }

    val visibleItems = lastVisiblePosition - firstVisiblePosition
    val numberOfAnimatedItems = position - 1

    if (visibleItems < numberOfAnimatedItems) {
        val numColumns = recyclerView?.getSpanCount()!!
        val n = position % numColumns
        delay += delayOffset * n
    } else {
        delay = position * delayOffset
    }

    return delay
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
) = animationWithDelayOffset(
    getViewModel<ViewModelType>()?.isFirstInit ?: false,
    itemAnimationRes,
    delayOffset,
    getRecyclerView()
)

fun DefaultViewHolder.updateAnimation(
    @AnimRes itemAnimationRes: Int = R.anim.item_animation_scale
) = animation(!(getViewModel<ViewModelType>()?.isFirstInit ?: false), itemAnimationRes)