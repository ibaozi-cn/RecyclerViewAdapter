package com.julive.adapter_demo.sorted

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.julive.adapter.core.DefaultViewHolder
import com.julive.adapter.sorted.SortedItemViewModel
import com.julive.adapter.sorted.SortedListAdapter
import com.julive.adapter_demo.R
import com.julive.adapter_demo.SortedModelTest

class SortedItemViewModelTest : SortedItemViewModel<SortedModelTest, DefaultViewHolder<SortedModelTest>>() {

    override fun getLayoutRes() = R.layout.item_test

    override fun getViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater
    ): DefaultViewHolder<SortedModelTest> {
        return ItemViewHolder(layoutInflater.inflate(layoutRes, parent, false))
    }

    override fun onBindViewHolder(
        viewHolder: DefaultViewHolder<SortedModelTest>,
        model: SortedModelTest,
        payloads: MutableList<Any>
    ) {
        viewHolder.getView<TextView>(R.id.tv_title)?.text = model.title
        viewHolder.getView<TextView>(R.id.tv_subTitle)?.text = model.subTitle
    }

    override fun unBindViewHolder(viewHolder: DefaultViewHolder<SortedModelTest>?) {

    }

}

class ItemViewHolder(view: View) : DefaultViewHolder<SortedModelTest>(view) {
    var index = 0
    init {
        itemView.setOnClickListener {
            val item =
                getAdapter<SortedListAdapter>()?.getItem(adapterPosition) as SortedItemViewModelTest
            item.model.subTitle = "刷新自己${++index}"
            getAdapter<SortedListAdapter>()?.updateItem(adapterPosition, item)
        }
    }
}