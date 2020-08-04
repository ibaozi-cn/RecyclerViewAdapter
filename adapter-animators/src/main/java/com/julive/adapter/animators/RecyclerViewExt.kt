package com.julive.adapter.animators

import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.Adapter<*>.intoWithAnimator(
    recyclerView: RecyclerView,
    layoutManager: RecyclerView.LayoutManager? = null,
    @AnimRes layoutAnimationRes: Int = R.anim.layout_animation_from_right
) = apply {
    recyclerView.layoutManager = layoutManager ?: LinearLayoutManager(recyclerView.context)
    recyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(recyclerView.context, layoutAnimationRes)
    recyclerView.adapter = this
}

fun RecyclerView.runLayoutAnimation(@AnimRes layoutAnimationRes: Int = R.anim.layout_animation_from_right) {
    val controller = AnimationUtils.loadLayoutAnimation(context, layoutAnimationRes)
    layoutAnimation = controller
    adapter?.notifyDataSetChanged()
    scheduleLayoutAnimation()
}
