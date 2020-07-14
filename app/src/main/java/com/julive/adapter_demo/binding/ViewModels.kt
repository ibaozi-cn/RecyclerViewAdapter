package com.julive.adapter_demo.binding

import com.julive.adapter.binding.BindingItemViewModel
import com.julive.adapter_demo.BR
import com.julive.adapter_demo.R
import com.julive.adapter_demo.sorted.ModelTest
import java.util.*

class BindingItemViewModelTest :
    BindingItemViewModel<ModelTest>(R.layout.item_binding_layout, BR.model) {

    init {

        onItemClick { viewModel, viewHolder ->
            val adapterPosition = viewHolder.adapterPosition
            viewModel.model?.title = "${Random().nextInt(100)}"
            adapter?.set(adapterPosition, viewModel)
        }

    }

}