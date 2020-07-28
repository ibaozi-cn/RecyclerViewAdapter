package com.julive.adapter_demo.binding

import com.julive.adapter.binding.BindingViewModel
import com.julive.adapter.core.ListAdapter
import com.julive.adapter_demo.BR
import com.julive.adapter_demo.R
import com.julive.adapter_demo.sorted.ModelTest
import java.util.*

class BindingItemViewModelTest :
    BindingViewModel<ModelTest>(R.layout.item_binding_layout, BR.model) {

    init {

        onItemClick { viewModel ->
            viewModel.model?.title = "${Random().nextInt(100)}"
            getAdapter<ListAdapter>()?.set(adapterPosition, viewModel)
        }

    }

}