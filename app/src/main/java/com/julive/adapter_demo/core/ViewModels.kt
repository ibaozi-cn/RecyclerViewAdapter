package com.julive.adapter_demo.core

import android.widget.TextView
import com.julive.adapter.animators.firstAnimation
import com.julive.adapter.animators.updateAnimation
import com.julive.adapter.core.*
import com.julive.adapter_demo.R
import com.julive.adapter_demo.sorted.ModelTest
import java.util.*

class ArrayViewModelTest : LayoutViewModel<ModelTest>(R.layout.item_test_2) {
    init {
        onCreateViewHolder {
            itemView.setOnClickListener {
                val vm = getAdapter<ListAdapter>()?.getItem(adapterPosition) as ArrayViewModelTest
                vm.model?.title = "${Random().nextInt(100)}"
                getAdapter<ListAdapter>()?.set(adapterPosition, vm)
            }
            onViewAttachedToWindow {
                firstAnimation()
//                updateAnimation()
            }
        }
    }

    override fun bindVH(viewHolder: DefaultViewHolder, payloads: List<Any>) {
        viewHolder.getView<TextView>(R.id.tv_title).text = model?.title
        viewHolder.getView<TextView>(R.id.tv_subTitle).text = model?.subTitle
    }
}