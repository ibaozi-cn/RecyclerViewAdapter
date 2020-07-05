package com.julive.adapter_demo.core

import android.view.View
import com.julive.adapter.core.ArrayItemViewModel
import com.julive.adapter.core.ArrayListAdapter
import com.julive.adapter_demo.R
import com.julive.adapter_demo.anko.ModelTest
import kotlinx.android.synthetic.main.item_test.view.*
import org.jetbrains.anko.toast

/**
 * ViewModel
 */
class ArrayViewModelTest : ArrayItemViewModel<ModelTest>() {

    var index = 0

    init {
        // 防止点击事件重复绑定，所以只需要在类初始化第一次时设置该绑定
        onItemClick = fun() {
            model.title = "${index++}"
            reBindView()
        }
    }

    // 防止onBindView 重复绑定时重新创建OnClickListener
    private val onTitleClick = View.OnClickListener {
        it.context.toast("点击标题")
    }

    override fun onBindView(adapter: ArrayListAdapter?) {
        viewHolder.itemView.apply {
            tv_title.text = model.title
            tv_subTitle.text = model.subTitle
            tv_title.setOnClickListener(onTitleClick)
        }
    }

    override fun getLayoutRes() = R.layout.item_test

}