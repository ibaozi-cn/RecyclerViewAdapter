package com.julive.adapter_demo.core

import com.julive.adapter.core.ArrayItemViewModel
import com.julive.adapter.core.ArrayListAdapter
import com.julive.adapter_demo.R
import com.julive.adapter_demo.anko.ModelTest
import kotlinx.android.synthetic.main.item_test.view.*

/**
 * ViewModel
 */
class ArrayViewModelTest : ArrayItemViewModel<ModelTest>() {

    var index = 0

    override fun onBindView(adapter: ArrayListAdapter?) {
        viewHolder.itemView.apply {
            tv_title.text = model.title
            tv_subTitle.text = model.subTitle
            cardItem.setOnClickListener {
                model.title = "${index++}"
                reBindView()
            }
        }
    }

    override fun getLayoutRes() = R.layout.item_test

}