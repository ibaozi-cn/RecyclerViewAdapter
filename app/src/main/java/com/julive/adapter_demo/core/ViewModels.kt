package com.julive.adapter_demo.core

import android.widget.TextView
import com.julive.adapter.core.LayoutViewModel
import com.julive.adapter.core.ListAdapter
import com.julive.adapter_demo.R
import com.julive.adapter_demo.binding.BindingItemViewModelTest
import com.julive.adapter_demo.sorted.ModelTest
import java.util.*

/**
 * ViewModel
 */

class ArrayViewModelTest : LayoutViewModel<ModelTest>(R.layout.item_test) {

    init {

        onBindViewHolder { _ ->
            getView<TextView>(R.id.tv_title)?.text = model?.title
            getView<TextView>(R.id.tv_subTitle)?.text = model?.subTitle
        }

        onCreateViewHolder {
            itemView.setOnClickListener {
                val vm = getAdapter<ListAdapter>()?.getItem(adapterPosition) as ArrayViewModelTest
                vm.model?.title = "${Random().nextInt(100)}"
                getAdapter<ListAdapter>()?.set(adapterPosition, vm)
            }
        }

    }

}