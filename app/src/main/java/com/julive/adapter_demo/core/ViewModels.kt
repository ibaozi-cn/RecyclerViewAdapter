package com.julive.adapter_demo.core

import android.util.Log
import android.view.View
import android.widget.TextView
import com.julive.adapter.core.ArrayItemViewModel
import com.julive.adapter.core.ArrayListAdapter
import com.julive.adapter.core.DefaultViewHolder
import com.julive.adapter_demo.ModelTest
import com.julive.adapter_demo.R
import kotlinx.android.synthetic.main.item_test.view.*
import org.jetbrains.anko.toast

/**
 * ViewModel
 */
class ArrayViewModelTest : ArrayItemViewModel<ModelTest, DefaultViewHolder>() {

    var index = 0

    override fun onBindView(adapter: ArrayListAdapter?) {
        getView<TextView>(R.id.tv_title)?.text = model.title
        getView<TextView>(R.id.tv_subTitle)?.text = model.subTitle
    }

    override fun getLayoutRes() = R.layout.item_test

    override fun getViewHolder(view: View): DefaultViewHolder {
        return DefaultViewHolder(view).apply {
            itemView.setOnClickListener {
                val item = adapter.get(adapterPosition) as ArrayViewModelTest
                item.model.title = "${index++}"
                adapter.set(adapterPosition, item)
            }
        }
    }

}