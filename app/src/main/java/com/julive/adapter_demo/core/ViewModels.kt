package com.julive.adapter_demo.core

import android.widget.TextView
import com.julive.adapter.core.ArrayItemViewModel
import com.julive.adapter_demo.ModelTest
import com.julive.adapter_demo.R
import java.util.*

/**
 * ViewModel
 */

class ArrayViewModelTest : ArrayItemViewModel<ModelTest>(R.layout.item_test) {

    init {

        onBindViewHolder { viewHolder ->
            viewHolder.getView<TextView>(R.id.tv_title)?.text = model?.title
            viewHolder.getView<TextView>(R.id.tv_subTitle)?.text = model?.subTitle
        }

        onItemClick { _, viewHolder ->
            val adapterPosition = viewHolder.adapterPosition
            val item =
                adapter?.getItem(adapterPosition) as ArrayViewModelTest
            item.model?.title = "${Random().nextInt(100)}"
            adapter?.set(adapterPosition, item)
        }

    }

}