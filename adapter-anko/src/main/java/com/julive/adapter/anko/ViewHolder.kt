package com.julive.adapter.anko

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.AnkoComponent

class AnkoViewHolder<AK : AnkoComponent<ViewGroup>>(val ankoView: AK, parent: ViewGroup) :
    RecyclerView.ViewHolder(ankoView.create(parent.context, parent))