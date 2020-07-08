package com.julive.adapter_demo.core

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.ArrayItemViewModel
import com.julive.adapter.core.ArrayItemViewModelDsl
import com.julive.adapter.core.ArrayListAdapter
import com.julive.adapter.core.DefaultViewHolder
import com.julive.adapter_demo.ModelTest
import com.julive.adapter_demo.R
import java.util.*

/**
 * ViewModel
 */

class ArrayViewModelTest : ArrayItemViewModelDsl<ModelTest>() {

    init {

        layoutId = R.layout.item_test

        onBindViewHolder { viewHolder ->
            viewHolder.getView<TextView>(R.id.tv_title)?.text = model.title
            viewHolder.getView<TextView>(R.id.tv_subTitle)?.text = model.subTitle
        }

        onItemClick { viewModel, viewHolder ->
            val adapterPosition = viewHolder.adapterPosition
            val item =
                adapter?.getItem(adapterPosition) as ArrayViewModelTest
            item.model.title = "${Random().nextInt(100)}"
            adapter?.set(adapterPosition, item)
        }

    }

}