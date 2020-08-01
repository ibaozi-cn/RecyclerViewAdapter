package com.julive.adapter_demo.core

import android.widget.TextView
import com.julive.adapter.core.*
import com.julive.adapter_demo.R
import com.julive.adapter_demo.sorted.ModelTest
import java.util.*

fun createViewModel() = layoutViewModelDsl<ModelTest>(R.layout.item_test_2) {

    val tvTitle = getView<TextView>(R.id.tv_title)
    val tvSubtitle = getView<TextView>(R.id.tv_subTitle)

    itemView.setOnClickListener {
        val vm = getAdapter<ListAdapter>()?.getItem(adapterPosition)
        getModel<ModelTest>()?.title = "${Random().nextInt(100)}"
        getAdapter<ListAdapter>()?.set(adapterPosition, vm)
    }

    onBindViewHolder { model, _ ->
        getView<TextView>(R.id.tv_title)?.text = model.title
        getView<TextView>(R.id.tv_subTitle)?.text = model.subTitle
    }

}