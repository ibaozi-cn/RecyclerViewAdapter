package com.julive.adapter_demo.sorted

import android.view.View
import com.julive.adapter.core.DefaultViewHolder
import com.julive.adapter.sorted.SortedItemViewModel
import com.julive.adapter.sorted.SortedListAdapter
import com.julive.adapter_demo.R
import com.julive.adapter_demo.SortedModelTest
import kotlinx.android.synthetic.main.item_test.view.*


class SortedItemViewModelTest : SortedItemViewModel<SortedModelTest,DefaultViewHolder>() {

    var index = 0

    override fun onBindView(adapter: SortedListAdapter) {
        viewHolder.itemView.apply {
            tv_title.text = model.title
            tv_subTitle.text = model.subTitle
        }
    }

    override fun getLayoutRes() = R.layout.item_test

    override fun getViewHolder(view: View): DefaultViewHolder {
        return DefaultViewHolder(view).apply {
            itemView.setOnClickListener {
                val item = adapter.getItem(adapterPosition) as SortedItemViewModelTest
                item.model.subTitle = "刷新自己${index++}"
                adapter.updateItem(adapterPosition,item)
            }
        }
    }

}