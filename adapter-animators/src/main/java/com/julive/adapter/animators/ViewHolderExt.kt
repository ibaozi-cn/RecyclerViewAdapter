package com.julive.adapter.animators

import android.view.View
import androidx.core.view.ViewPropertyAnimatorCompat
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.DefaultViewHolder

class AnimatorViewHolder(
    itemView: View,
    val addAnimation: (holder: RecyclerView.ViewHolder) -> ViewPropertyAnimatorCompat,
    val preAddAnimation: (holder: RecyclerView.ViewHolder) -> Unit,
    val removeAnimation: (holder: RecyclerView.ViewHolder) -> ViewPropertyAnimatorCompat,
    val preRemoveAnimation: (holder: RecyclerView.ViewHolder) -> Unit
) : DefaultViewHolder(itemView)