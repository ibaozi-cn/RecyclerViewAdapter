package com.julive.adapter_demo.sorted

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.ArrayListAdapter
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
        viewHolder: RecyclerView.ViewHolder?,
        model: SortedModelTest?,
        payloads: MutableList<Any>?
    ) {
    }

    override fun unBindViewHolder(viewHolder: RecyclerView.ViewHolder?) {
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
    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        item: SortedModelTest,
        payloads: List<Any>
    ) {
        getView<TextView>(R.id.tv_title)?.text = item.title
        getView<TextView>(R.id.tv_subTitle)?.text = item.subTitle
    }

    override fun unBindViewHolder(viewHolder: RecyclerView.ViewHolder) {
    }
}