package com.julive.adapter.animators

import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import androidx.recyclerview.widget.RecyclerView

class DefaultItemAnimator : BaseItemAnimator() {

    override fun removeAnimation(holder: RecyclerView.ViewHolder): ViewPropertyAnimatorCompat {
        return ViewCompat.animate(holder.itemView)
            .translationX(-holder.itemView.rootView.width * .25f)
            .alpha(0f)
            .setDuration(removeDuration)
            .setInterpolator(mInterpolator)
    }

    override fun preAddAnimation(holder: RecyclerView.ViewHolder) {
        holder.itemView.translationX = -holder.itemView.rootView.width * .25f
        holder.itemView.alpha = 0f
    }

    override fun addAnimation(holder: RecyclerView.ViewHolder): ViewPropertyAnimatorCompat {
        return ViewCompat.animate(holder.itemView)
            .translationX(0f)
            .alpha(1f)
            .setDuration(addDuration)
            .setInterpolator(mInterpolator)
    }

    override fun changeNewAnimation(holder: RecyclerView.ViewHolder): ViewPropertyAnimatorCompat {
        return ViewCompat.animate(holder.itemView)
            .translationY(0f)
            .setDuration(300)
    }

    override fun changeOldAnimation(
        holder: RecyclerView.ViewHolder,
        changeInfo: ChangeInfo
    ): ViewPropertyAnimatorCompat {
        return ViewCompat.animate(holder.itemView)
            .translationY(0f)
            .setDuration(300)
    }

}