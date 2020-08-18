package com.julive.adapter_demo.core

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.animators.firstAnimation
import com.julive.adapter.core.*
import com.julive.adapter_demo.R
import com.julive.adapter_demo.sorted.ModelTest
import java.util.*

class ArrayViewModelTest : LayoutViewModel<ModelTest>(R.layout.item_test_2) {
    init {
        onCreateViewHolder {
            val titleText = getView<TextView>(R.id.tv_title)
            val subTitleText = getView<TextView>(R.id.tv_subTitle)
            itemView.setOnClickListener {
                val vm = getViewModel<ArrayViewModelTest>()
                vm?.model?.title = "${Random().nextInt(100)}"
                getAdapter<ListAdapter>()?.set(adapterPosition, vm)
            }
            onViewAttachedToWindow {
                firstAnimation()
//                updateAnimation()
            }
            onBindViewHolder {
                val model = getModel<ModelTest>()
                titleText.text = model?.title
                subTitleText.text = model?.subTitle
            }
        }
    }
}